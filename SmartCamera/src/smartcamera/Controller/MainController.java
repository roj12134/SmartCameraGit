/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcamera.Controller;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javax.swing.SwingUtilities;
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
    private ArrayList<ImageIcon> imagesList = new ArrayList<ImageIcon>();
    private ImageIcon nofoto;//el nombre lo dice todo
    int foto = 0, antFoto = 0;
    private String sr = "";
    private String status = "";

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

    // Para el puerto serial 
    RXTX portSerial = null;

    Robot robot = null;

    // Color del lapiz
    Color colorNew = new Color(255, 255, 255);
    Color colorNow = new Color(255, 255, 255);

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
        status = "LivePanel"; // Empieza en live Panel
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
        mv.getPhoto().addMouseListener(this); // La foto que estoy viendo 
        mv.getColorButton().addMouseListener(this);

        // Joystick 
        mv.getJoystickPanel().addMouseMotionListener(this);
        mv.getJoystickPanel().addMouseListener(this);

        // Instancio el puerto RXTX, donde me conectare, asi mismo 
        // Empezare a leer el puerto serial 
        portSerial = new RXTX(view, this);
        try {
            portSerial.connect(); // Me conecto y empiezo a leer 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en puerto serial, al momento de conectarse " + ex, "Error en puerto serial", 0);
        }

        // Para pintar con Joystick 2 
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            JOptionPane.showMessageDialog(null, "Error en clase robot, al momento de instanciarla " + ex, "Error en clase robot", 0);
        }

        colorNow = colorNew;
        view.getColorPanelChoose().setBackground(colorNow);
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
            status = "LivePanel"; // Regresa a live Panel

        } else if (e.getSource() == view.getTakePhotoButton()) {

           takePhotoAction();

        } else if (e.getSource() == view.getEraseButton()) {

            cleanPanel();

        } else if (e.getSource() == view.getSaveButton()) {

            savePhotoEdited();

        } else if (e.getSource() == view.getGalleryButton() || e.getSource() == view.getGalleryButton2()) {
            //este evento tomara el panel de la galeria y mostrará todas las fotos que esten en la carpeta de imagenes
            sr = "Saved";
            imagenes(sr);//coloca un array con todas las fotos que esten en una carpeta
            view.setPhoto(getPreview(foto));//muestra un preview de la primera foto que este en la carpeta
            view.getPanelsContainer().removeAll();//quita todos los panels de contenedor
            view.getPanelsContainer().add(view.getGalleryPanel());//coloca el pane de la galeria
            status = "GallerySaved"; // Se va al status de galler saved
        } else if (e.getSource() == view.getEditButton()) {
            //este evento tomara el panel de la galeria y mostrará todas las fotos que esten en la carpeta de imagenes
            sr = "Taken";
            imagenes(sr);//coloca un array con todas las fotos que esten en una carpeta
            view.setPhoto(getPreview(foto));//mue1stra un preview de la primera foto que este en la carpeta
            view.getPanelsContainer().removeAll();//quita todos los panels de contenedor
            view.getPanelsContainer().add(view.getGalleryPanel());//coloca el pane de la galeria
            status = "GalleryTaken"; // Se va al status de gallery taken
        } else if (e.getSource() == view.getBack()) {
            //Este evento pondra la siguiente fotografia en el preview
            if (imagesList.size() > 0 && foto >= 1) {
                foto -= 1;
            } else if (imagesList.size() > 0 && foto == 0) {
                foto = imagesList.size() - 1;
            } else if (imagesList.size() == 1) {
                foto = 0;
            }
            view.setPhoto(getPreview(foto));//muestra un preview de la  foto que este seleccionada de la carpeta
        } else if (e.getSource() == view.getNext()) {
            //Este evento pondra la siguiente fotografia en el preview
            if (imagesList.size() > 0 && foto < imagesList.size() - 1) {
                foto += 1;
            } else if (imagesList.size() > 0 && foto == imagesList.size() - 1) {
                foto = 0;
            } else if (imagesList.size() == 1) {
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
            } else {
                JOptionPane.showMessageDialog(null, "No hay imagenes que puedan ser borradas.");
            }
            if (imagesList.isEmpty()) {
                foto = 0;
            } else if ((imagesList.size() - 1) >= 0 && foto == (imagesList.size() - 2)) {
                foto = 0;
            } else {
                foto += 1;
            }
            imagesList.remove(antFoto);
            if (foto >= imagesList.size() || foto < 0) {
                foto = 0;
            }
            view.setPhoto(getPreview(foto));//muestra un preview de la  foto que este seleccionada de la carpeta

        } else if (e.getSource() == view.getPhoto()) {
            if (status.equalsIgnoreCase("GalleryTaken")) {

                String path = "";

                // esto verifica si puede abrir archivos la computadora 
                path = (SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "Edited" + File.separator + "edit.jpg");//
                ImageIcon icon = imagesList.get(foto); // La foto de ahorita 

                BufferedImage bi = new BufferedImage(
                        icon.getIconWidth(),
                        icon.getIconHeight(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.createGraphics();
                // paint the Icon to the BufferedImage.
                icon.paintIcon(null, g, 0, 0);
                g.dispose();

                try {
                    ImageIO.write(bi, "jpg", new File(path));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear foto " + ex, "Error al crear foto ", 0);

                }

                // En este punto ya tengo la imagen como BufferImage 
                view.getPanelsContainer().removeAll();
                view.getPanelsContainer().add(view.getEditViewPanel());
                status = "EditPanel"; // Empieza en live Panel
                takenPhoto = bi;  // La foto que editare 
                view.setBackgroundImage(takenPhoto); // Cambio y doy repaint
                cleanPanel(); // Limpio si hay algo pintado 

                /**
                 * Set the cursor on the middle of photoView Panel
                 */
                robot.mouseMove(view.getPhotoView().getX() + view.getPhotoView().getWidth() / 2, (view.getPhotoView().getY() + view.getPanelName().getHeight() + 15) + (view.getPhotoView().getHeight() / 2));

            } else if (status.equalsIgnoreCase("GallerySaved")) {

                String path = "";
                // Codigo para abrir el archivo  
                if (Desktop.isDesktopSupported()) {
                    try {
                        // esto verifica si puede abrir archivos la computadora 
                        path = (SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "Opened" + File.separator + "open.jpg");//
                        ImageIcon icon = imagesList.get(foto); // La foto de ahorita 

                        BufferedImage bi = new BufferedImage(
                                icon.getIconWidth(),
                                icon.getIconHeight(),
                                BufferedImage.TYPE_INT_RGB);
                        Graphics g = bi.createGraphics();
                        // paint the Icon to the BufferedImage.
                        icon.paintIcon(null, g, 0, 0);
                        g.dispose();
                        ImageIO.write(bi, "jpg", new File(path));

                        Desktop.getDesktop().open(new File(path));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error en abrir archivo " + ex + "\n" + path, "Error en abrir archivo", 0);

                    }

                }

            }
        } else if (e.getSource() == view.getColorButton()) {
            changeColor();

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getSource() == view.getJoystickPanel()) {
            view.leftMouseButton = SwingUtilities.isLeftMouseButton(e);
            mouseCheck(e);

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getSource() == view.getDrawPanel()) {
            MainView._state = MainView.State.IDLE;
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == view.getDrawPanel()) {

            setPencilCursor();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource() == view.getDrawPanel()) {
            view.setCursor(null);

        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == view.getDrawPanel()) {

            MainView._state = MainView.State.DRAGGING;
            MainView._end = e.getPoint();
            if (MainView._state == MainView.State.DRAGGING) {
                // Panel para pintar 
                Graphics2D gioPaint = MainView._bufImage.createGraphics();
                gioPaint.setColor(colorNow);
                gioPaint.setStroke(new BasicStroke(3));
                gioPaint.drawLine(MainView._start.x, MainView._start.y, MainView._end.x, MainView._end.y);
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

        } else if (e.getSource() == view.getJoystickPanel()) {
            mouseCheck(e);
        }

    }

    // Metodo usado para joystick 
    private void mouseCheck(final MouseEvent e) {
        view.mouseX = e.getX();
        view.mouseY = e.getY();
        float dx = view.mouseX - view.joyCenterX;
        float dy = view.mouseY - view.joyCenterY;
        if (view.leftMouseButton) {
            view.isMouseTracking = true;
        } else {
            view.isMouseTracking = false;
        }
        if (view.isMouseTracking) {
            view.curJoyAngle = (float) Math.atan2(dy, dx);
            view.curJoySize = (float) Point.distance(view.mouseX, view.mouseY,
                    view.joyCenterX, view.joyCenterY);
        } else {
            view.curJoySize = 0;
        }
        if (view.curJoySize > view.joySize) {
            view.curJoySize = view.joySize;
        }
        view.position.x = (int) (view.joyOutputRange * (Math.cos(view.curJoyAngle)
                * view.curJoySize) / view.joySize);
        view.position.y = (int) (view.joyOutputRange * (-(Math.sin(view.curJoyAngle)
                * view.curJoySize) / view.joySize));
        //SwingUtilities.getRoot(view.getJoystickPanel().getRootPane()).repaint();
        view.position.x += 255;
        view.position.x /= 2;

        view.position.y += 255;
        view.position.y /= 2;
        view.getJoystickPanel().repaint();

    }
    /**
     * Function which takes photo 
     */
    public void takePhotoAction(){
         view.getPanelsContainer().removeAll();
            view.getPanelsContainer().add(view.getEditViewPanel());
            status = "EditPanel"; // Empieza en live Panel
            takenPhoto = buff;
            view.setBackgroundImage(takenPhoto); // Cambio y doy repaint
            cleanPanel(); // Limpio si hay algo pintado 

            /**
             * Set the cursor on the middle of photoView Panel
             */
            robot.mouseMove(view.getPhotoView().getX() + view.getPhotoView().getWidth() / 2, (view.getPhotoView().getY() + view.getPanelName().getHeight() + 15) + (view.getPhotoView().getHeight() / 2));

            // Guardarla en archivo 
            // Save as JPEG
            File file = new File(SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "Taken" + File.separator + "take" + getTimeNow() + ".jpg");
            try {
                ImageIO.write(takenPhoto, "jpg", file);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
     * Este metodo lo que hace es que calcula el tiempo en milisegundos y lo
     * devuelve en un String
     *
     * @return TiempoenMilesegundos
     */
    public String getTimeNow() {

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
        return (sdf.format(date));
    }

    //devuelve la ruta de acceso
    public String getFile(int g) {
        return imagesList.get(g).toString().substring(6);
    }

    public void imagenes(String src) {
        //Toma las fotografias que esten en la carpeta y las asigna a una lista de imagenesString sDirectorio = System.getProperty("user.dir")+"\\Images";
        String sDirectorio = SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + src;
        File f = new File(sDirectorio);
        //System.out.println(""+sDirectorio+" "+sr);
        // Recuperamos la lista de ficheros
        File[] ficheros = f.listFiles();
        imagesList.clear();
        if (f.exists()) {
            for (int x = 0; x < ficheros.length; x++) {
                try {
                    if (ficheros[x].toString().endsWith(".jpg")) {
                        //System.out.println(ficheros[x].toString());
                        //Se colocan las imagenes en la lista
                        imagesList.add(new javax.swing.ImageIcon(ficheros[x].toURL()));
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
        if (num >= 0 & num < imagesList.size())//en caso de que si hayan fotos
        {
            //coloca la imagen que se desea en el label con el tamaño del label
            Image mini = imagesList.get(num).getImage();
            return new ImageIcon(mini);
        } else {
            //coloca la imagen en 
            Image mini = nofoto.getImage();
            return new ImageIcon(mini);
        }
    }

    /**
     * Este metodo es llamado desde el controller de RXTX cada vez que lee datos
     */
    public void receiveData() {
        if (status.equalsIgnoreCase("EditPanel")) {

            view.getStatusLabel().setText("(" + RXTX.dataList.get(5) + "," + RXTX.dataList.get(6) + ")");

            if (toBinary(RXTX.dataList.get(7)).substring(7, 8).equalsIgnoreCase("1")) {
                // If able 
                robot.mousePress(InputEvent.BUTTON1_MASK);

            } else {
                robot.mouseRelease(InputEvent.BUTTON1_MASK);

            }

            int mouseX = MouseInfo.getPointerInfo().getLocation().x;
            int mouseY = MouseInfo.getPointerInfo().getLocation().y;
            int move = 10;

            if (RXTX.dataList.get(5) > 150) {
                mouseX += move;
            }
            if (RXTX.dataList.get(5) < 90) {
                mouseX -= move;
            }
            if (RXTX.dataList.get(6) > 150) {
                mouseY -= move;
            }
            if (RXTX.dataList.get(6) < 90) {
                mouseY += move;
            }

            robot.mouseMove(mouseX, mouseY);

            // Vamos a ver el boton borrar 
            if (toBinary(RXTX.dataList.get(7)).substring(6, 7).equalsIgnoreCase("1")) {
                cleanPanel();

            }
            // Valores de RGB 
            int redValue = RXTX.dataList.get(0);
            int greenValue = RXTX.dataList.get(1);
            int blueValue = RXTX.dataList.get(2);
            colorNew = new Color(redValue, greenValue, blueValue);

            // Vamos a ver el boton de colorear 
            if (toBinary(RXTX.dataList.get(7)).substring(5, 6).equalsIgnoreCase("1")) {
                changeColor();

            }
            // Vamos a ver el boton de colorear 
            if (toBinary(RXTX.dataList.get(7)).substring(4, 5).equalsIgnoreCase("1")) {
                savePhotoEdited();

            }

        }
        else if (status.equalsIgnoreCase("LivePanel")){
            // Live Panel 
            // Vamos a ver el boton de tomar foto  
            if (toBinary(RXTX.dataList.get(7)).substring(3, 4).equalsIgnoreCase("1")) {
                takePhotoAction(); // Toma la foto 

            }
            
        }
    }

    /**
     * Set the cursor in shape of pencil
     */
    public void setPencilCursor() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        ImageIcon ii = new javax.swing.ImageIcon(SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "GUI" + File.separator + "pencil.png");//foto en caso de que no haya una foto que mostrar
        Image imagen = ii.getImage();

        Point hotSpot = new Point(0, 0);
        Cursor cursor = tk.createCustomCursor(imagen, hotSpot, "Draw");
        view.setCursor(cursor); // cambio el cursor 

    }

    /**
     * Clear the draw panel
     */
    public void cleanPanel() {

        Graphics2D gio = MainView._bufImage.createGraphics();

        gio.clearRect(0, 0, view.getDrawPanel().getWidth(), view.getDrawPanel().getHeight());
        _bufImage = new BufferedImage(view.getDrawPanel().getWidth(), view.getDrawPanel().getHeight(), BufferedImage.TRANSLUCENT);

        gio.drawImage(MainView._bufImage, 0, 0, view.getDrawPanel().getWidth(), view.getDrawPanel().getHeight(), null);
        view.getDrawPanel().repaint();
        view.getPhotoView().repaint();

    }

    /**
     * Save the photo edited
     */
    public void savePhotoEdited() {

        try {
            // capture the whole screen
            BufferedImage screencapture = new Robot().createScreenCapture(
                    new Rectangle(view.getPhotoView().getX(), view.getPhotoView().getY() + view.getPanelName().getHeight() + 15,
                            view.getPhotoView().getWidth(), view.getPhotoView().getHeight()));

            // Save as JPEG
            File file = new File(SmartCamera.getPathJar() + File.separator + "src" + File.separator + "smartcamera" + File.separator + "Images" + File.separator + "Saved" + File.separator + "save" + getTimeNow() + ".jpg");
            ImageIO.write(screencapture, "jpg", file);

            // go to the Main
            view.getPanelsContainer().removeAll();
            view.getPanelsContainer().add(view.getViewLivePanel());
            status = "LivePanel"; // Regresa a live Panel
            view.setCursor(null);

        } catch (AWTException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Change Color to draw
     */
    public void changeColor() {
        colorNow = colorNew;
        view.getColorPanelChoose().setBackground(colorNow);
    }

    /**
     * Utils for the program
     *
     */
    /*
     Conversion binary to decimal and viceversa
     */
    //De binario a decimal
    private int toDecimal(String numeroBinario) {

        int num = Integer.parseInt(numeroBinario, 2);
        return num;
        //System.out.println(numeroBinario + " base 2 = " + num + " base 10");
    }

    //De decimal a binario
    private String toBinary(int numeroDecimal) {

        String bytes = Integer.toBinaryString(numeroDecimal);
        int missing = 8 - bytes.length();
        String zeroAdd = "";
        while (missing != 0) {
            zeroAdd += "0";
            missing--;
        }

        return ((zeroAdd + bytes)); // Devuelvo el binario con los 0 ideales 
    }

    // Decimal a Hex
    private String hexToDecimal(Integer a) {
        String hex = Integer.toHexString(a);
        return hex;
    }

    /**
     * A Class for video on JPanel
     */
    public class DaemonThread implements Runnable {

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
