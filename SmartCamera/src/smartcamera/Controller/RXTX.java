/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcamera.Controller;

/**
 *
 * @author Javier Alay
 */
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import smartcamera.View.MainView;

public class RXTX {

    public static SerialPort serialPort;
    public static String dataVector;
    private static MainView view;
    private static MainController controller;
    public static ArrayList <Integer> dataList;

    public RXTX(MainView view, MainController controller) {
        this.view = view;  // Defino mi vista
        this.controller = controller; // Defino mi controller 
        
        dataList = new ArrayList<Integer>();
    }
    
    

    public void connect() throws Exception {

        String SerialPortID = null;

        if (isWindows() || isMac()) {

            SerialPortID = "C0M10";
            
            Enumeration ports = CommPortIdentifier.getPortIdentifiers();

            while (ports.hasMoreElements()) {
                CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
                System.out.println(port.getName());
            }

        } else {
            
            SerialPortID = "/dev/ttyAMA0";
            System.setProperty("gnu.io.rxtx.SerialPorts", SerialPortID);
        }

        CommPortIdentifier portIdentifier
                = CommPortIdentifier.getPortIdentifier(SerialPortID);

        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            int timeout = 2000;
            CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);

            if (commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                InputStream in = serialPort.getInputStream();

                (new Thread(new SerialReader(in))).start(); // Empieza a leer 

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    /**
     *
     * Mira si es un Windows la computadora
     */
    public boolean isWindows() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("win") >= 0);

    }

    /**
     * Mira si es o no Mac
     *
     * @return si es mac o no
     */
    public boolean isMac() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("mac") >= 0);

    }
    
    

    public void serialWriter(byte[] sendData) {
        OutputStream out;
        try {
            out = serialPort.getOutputStream();
            int c = 0;
            while (c < sendData.length) {
                out.write(sendData[c]);
                c++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class SerialReader implements Runnable {

        private InputStream in;
        private int inData = -1;

        public SerialReader(InputStream in) {
            this.in = in;
        }

        public void run() {

            try {
                while (true) {
                    // Inicio el ciclo limpiando la lista
                    dataList = new ArrayList<Integer>();
                    inData = in.read();
                    if ((Character.toString((char) inData)).equalsIgnoreCase("R")) {

                        inData = in.read();

                        if ((Character.toString((char) inData)).equalsIgnoreCase("O")) {
                            inData = in.read();

                            if ((Character.toString((char) inData)).equalsIgnoreCase("J")) {
                                inData = in.read();

                                if ((Character.toString((char) inData)).equalsIgnoreCase("A")) {
                                    inData = in.read();

                                    if ((Character.toString((char) inData)).equalsIgnoreCase("S")) {
                                        
                                        // RGB
                                        inData = in.read();
                                        dataList.add(inData);
                                        System.out.println("RED Value : " + inData);
                                        inData = in.read();
                                        dataList.add(inData);
                                        System.out.println("GREEN Value : " + inData);
                                        inData = in.read();
                                        dataList.add(inData);
                                        System.out.println("BLUE Value : " + inData);
                                        
                                        // Joystick 1 
                                        inData = in.read();
                                        dataList.add(inData);
                                        System.out.println("X1 : " + inData);
                                        inData = in.read();
                                        dataList.add(inData);
                                        System.out.println("Y1 : " + inData);
                                        
                                        // Joystick 2
                                        inData = in.read();
                                        dataList.add(inData);
                                        System.out.println("X2 : " + inData);
                                        inData = in.read();
                                        dataList.add(inData);
                                        System.out.println("Y2 : " + inData);
                                        
                                        //Botones
                                        inData = in.read();
                                        dataList.add(inData);
                                        System.out.println("BOTONES : " + inData);
                                        
                                        // Abble Joysticks
                                        inData = in.read();
                                        dataList.add(inData);
                                        System.out.println("ABBLE JOYSTICKS : " + inData);
                                        
                                        // STATUS 
                                        view.getStatusLabel().setText("READING ...");
                                        
                                        controller.receiveData();
                                    }

                                }

                            }

                        }

                    } else {

                        System.out.println("Otro data : " + inData);

                    }

                }
                
            } catch (IOException e) {
                view.getStatusLabel().setText("ERROR"); // si sale, ya no estara leyendo 
                e.printStackTrace();
            }
        }
    }
}
