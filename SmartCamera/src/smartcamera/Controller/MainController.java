/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcamera.Controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
import smartcamera.View.MainView;

/**
 *
 * @author Gio
 */
public class MainController implements MouseListener {

    // The MVC
    private MainView view = null;
    private MainModel model = null;
    private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
    private ImageIcon nofoto;//el nombre lo dice todo
    private int foto=0;

    /**
     * Define vars of the program.
     */
    private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = null;

    Mat frameLive = new Mat();
    MatOfByte mem = new MatOfByte();

    public MainController(MainView mv, MainModel mm) {
        view = mv; // Instancio la vista aqui 
        model = mm;  // Instancio el modelo aqui 

        // After the start y will start the video 
        webSource = new VideoCapture(0);
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
        mv.getEditButton().addMouseListener(this);
        mv.getGoToCameraButton().addMouseListener(this);
        mv.getGoToCameraButton2().addMouseListener(this);
        mv.getTakePhotoButton().addMouseListener(this);
        mv.getGalleryButton().addMouseListener(this);
        mv.getGalleryButton2().addMouseListener(this);
        mv.getNext().addMouseListener(this);
        mv.getBack().addMouseListener(this);

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

        } else if (e.getSource() == view.getEditButton()) {
            view.getPanelsContainer().removeAll();
            view.getPanelsContainer().add(view.getEditViewPanel());

        } else if (e.getSource() == view.getGoToCameraButton() || e.getSource() == view.getGoToCameraButton2()) {
            view.getPanelsContainer().removeAll();
            view.getPanelsContainer().add(view.getViewLivePanel());

        } else if (e.getSource() == view.getTakePhotoButton()) {

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
        } else if (e.getSource() == view.getGalleryButton() || e.getSource() == view.getGalleryButton2()){
            //este evento tomara el panel de la galeria y mostrará todas las fotos que esten en la carpeta de imagenes
            imagenes();//coloca un array con todas las fotos que esten en una carpeta
            view.setPhoto(getPreview(foto));//muestra un preview de la primera foto que este en la carpeta
            view.getPanelsContainer().removeAll();//quita todos los panels de contenedor
            view.getPanelsContainer().add(view.getGalleryPanel());//coloca el pane de la galeria
        } else if(e.getSource() == view.getBack()){
            //Este evento pondra la siguiente fotografia en el preview
            if(images.size()>0 && foto>0)
                foto--;
            else if(images.size()>0 && foto==0)
                foto=images.size()-1;
            else if(images.size()==1)
                foto=0;
            view.setPhoto(getPreview(foto));//muestra un preview de la  foto que este seleccionada de la carpeta
        } else if(e.getSource() == view.getNext()){
            //Este evento pondra la siguiente fotografia en el preview
            if(images.size()>0 && foto<images.size()-1)
                foto++;
            else if(images.size()>0 && foto==images.size()-1)
                foto=0;
            else if(images.size()==1)
                foto=0;
            view.setPhoto(getPreview(foto));//muestra un preview de la  foto que este seleccionada de la carpeta
        }
        

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void imagenes(){
        String sDirectorio = System.getProperty("user.dir")+"\\Images";
        File f = new File(sDirectorio);
        //System.out.println(f.getAbsolutePath());
        if (f.exists()){
                // Recuperamos la lista de ficheros
                File[] ficheros = f.listFiles();
                for (int x=0;x<ficheros.length;x++){
                    try {
                        //System.out.println(ficheros[x].getName());
                        images.add(new javax.swing.ImageIcon(ficheros[x].toURL()));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        } else{
                JOptionPane.showMessageDialog(null, "No hay fotos.");
                //System.out.println("No existe ese directorio");
        }
    }
    
    public Icon getPreview(int num){
        nofoto = new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\src\\smartcamera\\Images\\GUI\\nofoto.gif");
        if( num>=0 & num<images.size() )
        {
            Image mini = images.get(num).getImage().getScaledInstance(view.getPhoto().getWidth(), view.getPhoto().getHeight(), Image.SCALE_AREA_AVERAGING);
            return new ImageIcon(mini);
        }
        else
        {
            Image mini = nofoto.getImage().getScaledInstance(view.getPhoto().getWidth(), view.getPhoto().getHeight(), Image.SCALE_AREA_AVERAGING);
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

                            BufferedImage buff = (BufferedImage) im;
                            Graphics g = view.getLivePanel().getGraphics();

                            if (g.drawImage(buff, 0, 0, view.getWidth(), view.getHeight() - 150, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
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
