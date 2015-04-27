/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcamera.Controller;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
        mv.getEditButton().addMouseListener(this);
        mv.getGoToCameraButton().addMouseListener(this);
        mv.getTakePhotoButton().addMouseListener(this);
        mv.getDrawPanel().addMouseListener(this);  // Panel de dibujo 
        mv.getDrawPanel().addMouseMotionListener(this); // Panel de dibujo 
        mv.getEraseButton().addMouseListener(this);
        mv.getSaveButton().addMouseListener(this);

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
            // Have to work on it 

        } else if (e.getSource() == view.getGoToCameraButton()) {
            view.getPanelsContainer().removeAll();
            view.getPanelsContainer().add(view.getViewLivePanel());

        } else if (e.getSource() == view.getTakePhotoButton()) {

            view.getPanelsContainer().removeAll();
            view.getPanelsContainer().add(view.getEditViewPanel());
            takenPhoto = buff;
            view.setBackgroundImage(takenPhoto); // Cambio y doy repaint

            // Guardarla en archivo 
            // Save as JPEG
            File file = new File(SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "Taken" + File.separator + "take.jpg");
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
                File file = new File(SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "Saved" + File.separator + "screencapture.jpg");
                ImageIO.write(screencapture, "jpg", file);

            } catch (AWTException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

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
