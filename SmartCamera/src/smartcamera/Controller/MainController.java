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
import javax.imageio.ImageIO;
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
        mv.getEditButton().addMouseListener(this);
        mv.getGoToCameraButton().addMouseListener(this);
        mv.getTakePhotoButton().addMouseListener(this);

    }

    /**
     * Whenever object recibe a clicked, it will manage by this method
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == view.getExitButton() || e.getSource() == view.getExitButton2()) {
            // Before I close the program I will stop the livePanel
            myThread.runnable = false;
            webSource.release();
            // Exit System
            System.exit(0);

        } else if (e.getSource() == view.getEditButton()) {
            view.getPanelsContainer().removeAll();
            view.getPanelsContainer().add(view.getEditViewPanel());

        } else if (e.getSource() == view.getGoToCameraButton()) {
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
