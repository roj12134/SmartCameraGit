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
import java.nio.file.Files;
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
    private int foto=0, antFoto=0;

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
        mv.getTrash().addMouseListener(this);

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
        } 
        else if (e.getSource() == view.getGalleryButton() || e.getSource() == view.getGalleryButton2()){
            //este evento tomara el panel de la galeria y mostrará todas las fotos que esten en la carpeta de imagenes
            imagenes();//coloca un array con todas las fotos que esten en una carpeta
            view.setPhoto(getPreview(foto));//muestra un preview de la primera foto que este en la carpeta
            view.getPanelsContainer().removeAll();//quita todos los panels de contenedor
            view.getPanelsContainer().add(view.getGalleryPanel());//coloca el pane de la galeria
        } 
        else if(e.getSource() == view.getBack()){
            //Este evento pondra la siguiente fotografia en el preview
            if(images.size()>0 && foto>0)
                foto--;
            else if(images.size()>0 && foto==0)
                foto=images.size()-1;
            else if(images.size()==1)
                foto=0;
            view.setPhoto(getPreview(foto));//muestra un preview de la  foto que este seleccionada de la carpeta
        } 
        else if(e.getSource() == view.getNext()){
            //Este evento pondra la siguiente fotografia en el preview
            if(images.size()>0 && foto<images.size()-1)
                foto++;
            else if(images.size()>0 && foto==images.size()-1)
                foto=0;
            else if(images.size()==1)
                foto=0;
            view.setPhoto(getPreview(foto));//muestra un preview de la  foto que este seleccionada de la carpeta
        } 
        else if(e.getSource() == view.getNext()){
            //Este evento pondra la siguiente fotografia en el preview
            if(images.size()>0 && foto<images.size()-1)
                foto++;
            else if(images.size()>0 && foto==images.size()-1)
                foto=0;
            else if(images.size()==1)
                foto=0;
            view.setPhoto(getPreview(foto));//muestra un preview de la  foto que este seleccionada de la carpeta
        } 
        else if(e.getSource() == view.getTrash()){
            String sDirectorio = System.getProperty("user.dir")+"\\Images";
            File f = new File(sDirectorio);
            // Recuperamos la lista de ficheros
            File[] ficheros = f.listFiles();
            antFoto=foto;
            //Este evento borra la imagen que este seleccionada en el preview
            if(images.size()>0 && foto<images.size()-1)
                foto++;
            else if(images.size()>0 && foto==images.size()-1)
                foto=0;
            else if(images.size()==1)
                foto=0;
            if(ficheros.length>0){
                if(ficheros[antFoto].delete())
                    JOptionPane.showMessageDialog(null, "La imagen ha sido borrada.");
                else
                    JOptionPane.showMessageDialog(null, "La imagen no se pudo borrar.");
            }
            else
                JOptionPane.showMessageDialog(null, "No hay imagenes que puedan ser borradas.");
            images.clear();
            imagenes();
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
        //Toma las fotografias que esten en la carpeta y las asigna a una lista de imagenesString sDirectorio = System.getProperty("user.dir")+"\\Images";
        String sDirectorio = System.getProperty("user.dir")+"\\Images";
        File f = new File(sDirectorio);
        // Recuperamos la lista de ficheros
        File[] ficheros = f.listFiles();

        if (f.exists()){
                for (int x=0;x<ficheros.length;x++){
                    try {
                        //Se colocan las imagenes en la lista
                        images.add(new javax.swing.ImageIcon(ficheros[x].toURL()));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        } else{
            //En caso de que no hayan fotos
            JOptionPane.showMessageDialog(null, "No hay fotos.");
        }
    }
    //Pone la imagen en el label como preview de la foto, en caso de no haber una foto pone una imagen de que no hay fotos que mostrar
    public Icon getPreview(int num){
        nofoto = new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\src\\smartcamera\\Images\\GUI\\nofoto.gif");//foto en caso de que no haya una foto que mostrar
        if( num>=0 & num<images.size() )//en caso de que si hayan fotos
        {
            //coloca la imagen que se desea en el label con el tamaño del label
            Image mini = images.get(num).getImage().getScaledInstance(view.getPhoto().getWidth(), view.getPhoto().getHeight(), Image.SCALE_AREA_AVERAGING);
            return new ImageIcon(mini);
        }
        else
        {
            //coloca la imagen en 
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
