/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecrunch_launcher;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author tstreit
 */
public class Minecrunch_launcher {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws net.lingala.zip4j.exception.ZipException
     */
    public static void main(String[] args) throws IOException, ZipException {
        if (Utility.HasConnectivity()) {
            Utility.GetResources();
            Utility.MinecraftCheck();
        } else {
            PackUI pi = new PackUI();
            pi.setVisible(true);
        }
    }
}
