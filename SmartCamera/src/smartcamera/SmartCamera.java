/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcamera;

import java.io.File;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import smartcamera.View.MainView;

/**
 *
 * @author Gio
 */
public class SmartCamera {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Load the class of OPENCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.NATIVE_LIBRARY_NAME.toString());

        MainView mv = new MainView();
        mv.setExtendedState(6);
        mv.setVisible(true);

    }

    /*
     Metodo usado en todo el programa para obtener donde esta el jar donde se esta ejecutando el programa 
     */
    public static String getPathJar() {
        try {
            return new File(SmartCamera.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
        } catch (URISyntaxException ex) {
            JOptionPane.showMessageDialog(null, "Error en URISyntax Expection ", "Error", 0);
            return null;
        }
    }

}
