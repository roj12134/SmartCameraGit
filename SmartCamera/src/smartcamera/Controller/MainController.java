/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcamera.Controller;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import smartcamera.Model.MainModel;
import smartcamera.SmartCamera;
import smartcamera.View.MainView;
import static smartcamera.View.MainView._bufImage;

/**
 *
 * @author Gio
 */
public class MainController implements MouseListener, MouseMotionListener {

    // The MVC
    private MainView view = null;
    private MainModel model = null;
    private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
    private ImageIcon nofoto;//el nombre lo dice todo
    int foto = 0, antFoto = 0;
    private String sr="";

    /**
     * Define vars of the program.
     */
    private DaemonThread myThread = null;
    private int count = 0;
    private VideoCapture webSource = null;

    private Mat frameLive = new Mat();
    private MatOfByte mem = new MatOfByte();

    // Var of the photo taken
    private BufferedImage buff = null;

    // taken Photo
    private BufferedImage takenPhoto = null;

    public MainController(MainView mv, MainModel mm) {
        view = mv; // Instancio la vista aqui 
        model = mm;  // Instancio el modelo aqui 

        // After the start y will start the video 
        webSource = new VideoCapture(0);

        webSource.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, mv.getLivePanel().getWidth());
        webSource.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, mv.getLivePanel().getHeight()); // Specifi Size of Web Cam 

        myThread = new DaemonThread();
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();

        mv.getPanelsContainer().removeAll();
        mv.getPanelsContainer().add(mv.getViewLivePanel()); // Set the first View of the program 

        // Voy a obtener todos los objetos de la vista para manejarlos aqui, 
        // y asignare los 
        // metodos correspondientes 
        mv.getExitButton().addMouseListener(this); // Events comes a live 
        mv.getExitButton2().addMouseListener(this);
        mv.getExitButton3().addMouseListener(this);
        mv.getGoToCameraButton().addMouseListener(this);
        mv.getGoToCameraButton2().addMouseListener(this);
        mv.getTakePhotoButton().addMouseListener(this);

        mv.getDrawPanel().addMouseListener(this);  // Panel de dibujo 
        mv.getDrawPanel().addMouseMotionListener(this); // Panel de dibujo 
        mv.getEraseButton().addMouseListener(this);
        mv.getSaveButton().addMouseListener(this);

        mv.getGalleryButton().addMouseListener(this);
        mv.getGalleryButton2().addMouseListener(this);
        mv.getNext().addMouseListener(this);
        mv.getBack().addMouseListener(this);
        mv.getEditButton().addMouseListener(this);
        mv.getTrash().addMouseListener(this);
        mv.getPhoto().addMouseListener(this);

    }

    /**
     * Whenever object recibe a clicked, it will manage by this method
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == view.getExitButton() || e.getSource() == view.getExitButton2() || e.getSource() == view.getExitButton3()) {
            // Before I close the program I will stop the livePanel
            myThread.runnable = false;
            webSource.release();
            // Exit System
            System.exit(0);

        } else if (e.getSource() == view.getGoToCameraButton() || e.getSource() == view.getGoToCameraButton2()) {
            view.getPanelsContainer().removeAll();
            view.getPanelsContainer().add(view.getViewLivePanel());

        } else if (e.getSource() == view.getTakePhotoButton()) {

            view.getPanelsContainer().removeAll();
            view.getPanelsContainer().add(view.getEditViewPanel());
            takenPhoto = buff;
            view.setBackgroundImage(takenPhoto); // Cambio y doy repaint

            // Guardarla en archivo 
            // Save as JPEG
            File file = new File(SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "Taken" + File.separator + "take"+getTimeNow()+".jpg");
            try {
                ImageIO.write(takenPhoto, "jpg", file);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (e.getSource() == view.getEraseButton()) {

            Graphics2D gio = MainView._bufImage.createGraphics();

            gio.clearRect(0, 0, view.getDrawPanel().getWidth(), view.getDrawPanel().getHeight());
            _bufImage = new BufferedImage(view.getDrawPanel().getWidth(), view.getDrawPanel().getHeight(), BufferedImage.TRANSLUCENT);

            gio.drawImage(MainView._bufImage, 0, 0, view.getDrawPanel().getWidth(), view.getDrawPanel().getHeight(), null);
            view.getDrawPanel().repaint();
            view.getPhotoView().repaint();

        } else if (e.getSource() == view.getSaveButton()) {
            

            try {
                // capture the whole screen
                BufferedImage screencapture = new Robot().createScreenCapture(
                        new Rectangle(view.getPhotoView().getX(), view.getPhotoView().getY() + view.getPanelName().getHeight() + 15,
                                view.getPhotoView().getWidth(), view.getPhotoView().getHeight()));

                // Save as JPEG
                File file = new File(SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "Save" + File.separator + "screencapture"+getTimeNow()+".jpg");
                ImageIO.write(screencapture, "jpg", file);

            } catch (AWTException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Take a photo old 
            /*
             VideoCapture camera = new VideoCapture(0);
             try {
             Thread.sleep(1000);
             } catch (Exception e) {
             e.printStackTrace();
             }
             if (!camera.isOpened()) {
             System.out.println("Camera Error");
             } else {
             System.out.println("Camera OK?");
             }

             Mat frame = new Mat();
             camera.read(frame);
             Highgui.imwrite(SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "Taken" + File.separator + "capture.jpg", frame);
             camera.release(); // Remember to release the camera
             */
        } else if (e.getSource() == view.getGalleryButton() || e.getSource() == view.getGalleryButton2()) {
            //este evento tomara el panel de la galeria y mostrará todas las fotos que esten en la carpeta de imagenes
            sr="Saved";
            imagenes(sr);//coloca un array con todas las fotos que esten en una carpeta
            view.setPhoto(getPreview(foto));//muestra un preview de la primera foto que este en la carpeta
            view.getPanelsContainer().removeAll();//quita todos los panels de contenedor
            view.getPanelsContainer().add(view.getGalleryPanel());//coloca el pane de la galeria
        } else if (e.getSource() == view.getEditButton()) {
            //este evento tomara el panel de la galeria y mostrará todas las fotos que esten en la carpeta de imagenes
            sr="Taken";
            imagenes(sr);//coloca un array con todas las fotos que esten en una carpeta
            view.setPhoto(getPreview(foto));//mue1stra un preview de la primera foto que este en la carpeta
            view.getPanelsContainer().removeAll();//quita todos los panels de contenedor
            view.getPanelsContainer().add(view.getGalleryPanel());//coloca el pane de la galeria
        } else if (e.getSource() == view.getBack()) {
            //Este evento pondra la siguiente fotografia en el preview
            if (images.size() > 0 && foto >= 1) {
                foto-=1;
            } else if (images.size() > 0 && foto == 0) {
                foto = images.size()-1;
            } else if (images.size() == 1) {
                foto = 0;
            }
            view.setPhoto(getPreview(foto));//muestra un preview de la  foto que este seleccionada de la carpeta
        } else if (e.getSource() == view.getNext()) {
            //Este evento pondra la siguiente fotografia en el preview
            if (images.size() > 0 && foto < images.size() - 1) {
                foto+=1;
            } else if (images.size() > 0 && foto == images.size() - 1) {
                foto = 0;
            } else if (images.size() == 1) {
                foto = 0;
            }
            view.setPhoto(getPreview(foto));//muestra un preview de la  foto que este seleccionada de la carpeta
        } else if (e.getSource() == view.getTrash()) {
            String sDirectorio = SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + sr;
            File f = new File(sDirectorio);
            // Recuperamos la lista de ficheros
            File[] ficheros = f.listFiles();
            antFoto = foto;
            //Este evento borra la imagen que este seleccionada en el preview
            if (ficheros.length > 0) {
                if (ficheros[antFoto].delete()) {
                    JOptionPane.showMessageDialog(null, "La imagen ha sido borrada.");
                } else {
                    JOptionPane.showMessageDialog(null, "La imagen no se pudo borrar.");
                }
            } 
            else {
                JOptionPane.showMessageDialog(null, "No hay imagenes que puedan ser borradas.");
            } 
            if (images.isEmpty()) {
                foto = 0;
            } else if ((images.size()-1)>=0 && foto==(images.size()-2)) {
                foto=0;
            } else{
                foto+=1;
            }
            images.remove(antFoto);
            if(foto>=images.size() || foto<0)
                foto=0;
            view.setPhoto(getPreview(foto));//muestra un preview de la  foto que este seleccionada de la carpeta
            
        } else if (e.getSource() == view.getPhoto()) {
            //Aquí se abrira la imagen en el panel para ser editada
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getSource() == view.getDrawPanel()) {
            MainView._state = MainView.State.IDLE;
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == view.getDrawPanel()) {

            MainView._state = MainView.State.DRAGGING;
            MainView._end = e.getPoint();
            if (MainView._state == MainView.State.DRAGGING) {
                Graphics2D g2 = MainView._bufImage.createGraphics();
                g2.setColor(Color.BLUE);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(MainView._start.x, MainView._start.y, MainView._end.x, MainView._end.y);
                view.getDrawPanel().repaint();
                view.getPhotoView().repaint();
            }
            MainView._start = MainView._end;
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if (e.getSource() == view.getDrawPanel()) {

            MainView._start = e.getPoint();

        }

    }
    
    public String getTimeNow(){
        
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
            return (sdf.format(date));
    }

    //devuelve la ruta de acceso
    public String getFile(int g) {
        return images.get(g).toString().substring(6);
    }

    public void imagenes(String src) {
        //Toma las fotografias que esten en la carpeta y las asigna a una lista de imagenesString sDirectorio = System.getProperty("user.dir")+"\\Images";
        String sDirectorio = SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + src;
        File f = new File(sDirectorio);
        //System.out.println(""+sDirectorio+" "+sr);
        // Recuperamos la lista de ficheros
        File[] ficheros = f.listFiles();
        images.clear();
        if (f.exists()) {
            for (int x = 0; x < ficheros.length; x++) {
                try {
                    if (ficheros[x].toString().endsWith(".jpg")) {
                        //System.out.println(ficheros[x].toString());
                        //Se colocan las imagenes en la lista
                        images.add(new javax.swing.ImageIcon(ficheros[x].toURL()));
                    }
                } catch (MalformedURLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            //En caso de que no hayan fotos
            JOptionPane.showMessageDialog(null, "No hay fotos.");
        }
    }

    //Pone la imagen en el label como preview de la foto, en caso de no haber una foto pone una imagen de que no hay fotos que mostrar
    public Icon getPreview(int num) {
        nofoto = new javax.swing.ImageIcon(SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "GUI" + File.separator + "noPhoto.png");//foto en caso de que no haya una foto que mostrar
        if (num >= 0 & num < images.size())//en caso de que si hayan fotos
        {
            //coloca la imagen que se desea en el label con el tamaño del label
            Image mini = images.get(num).getImage();
            return new ImageIcon(mini);
        } else {
            //coloca la imagen en 
            Image mini = nofoto.getImage();
            return new ImageIcon(mini);
        }
    }

    /**
     * A Class for video on JPanel
     */
    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    if (webSource.grab()) {
                        try {
                            webSource.retrieve(frameLive);

                            Highgui.imencode(".bmp", frameLive, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

                            buff = (BufferedImage) im;
                            Graphics g = view.getLivePanel().getGraphics();

                            //if (g.drawImage(buff, 0, 0, view.getWidth(), view.getHeight() - 150, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                            if (g.drawImage(buff, 0, 0, view.getLivePanel().getWidth(), view.getLivePanel().getHeight(), null)) {

                                if (runnable == false) {
                                    System.out.println("Going to wait()");
                                    this.wait();
                                }
                            }
                        } catch (Exception ex) {
                            // Not use, appears when I set visible false the panel 
                            // System.out.println("Error");
                        }
                    }
                }
            }
        }
    }

}
