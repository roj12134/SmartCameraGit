/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcamera;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Gio
 */
public class SmartCamera {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // OR load the native library this way.
        // System.loadLibrary("opencv_java244");
        System.out.println("Hello, OpenCV");

        Mat frame = new Mat();
        VideoCapture cap = new VideoCapture(0);

        try {
            Thread.sleep(500);	// 0.5 sec of a delay. This is not obvious but its necessary
            // as the camera simply needs time to initialize itself
        } catch (InterruptedException ex) {
            Logger.getLogger(SmartCamera.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!cap.isOpened()) {
            System.out.println("Did not connect to camera");
        } else {
            System.out.println("found webcam: " + cap.toString());
        }

        cap.retrieve(frame);// The current frame in the camera is saved in "frame"
        System.out.println("Captured image with " + frame.width() + " pixels wide by " + frame.height() + " pixels tall.");
        Highgui.imwrite("me1.jpg", frame);
        Mat frameBlur = new Mat();
        Imgproc.blur(frame, frameBlur, new Size(5, 5));
        Highgui.imwrite("me2-blurred.jpg", frameBlur);

        cap.release(); // Remember to release the camera
    }

}
