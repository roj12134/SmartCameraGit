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

public class RXTX {
    public static SerialPort serialPort;
    public static String inData;
    void connect() throws Exception {
        String SerialPortID = "/dev/ttyAMA0";
	System.setProperty("gnu.io.rxtx.SerialPorts", SerialPortID);
        
        CommPortIdentifier portIdentifier = CommPortIdentifier
                .getPortIdentifier(SerialPortID);
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

                (new Thread(new SerialReader(in))).start();

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }
    
    public void serialWriter(byte[] sendData){
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

    public static class SerialReader implements Runnable {

        InputStream in;

        public SerialReader(InputStream in) {
            this.in = in;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int len = -1;
            try {
                while ((len = this.in.read(buffer)) > -1) {
                    inData = new String(buffer, 0, len);
                    System.out.println(inData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
