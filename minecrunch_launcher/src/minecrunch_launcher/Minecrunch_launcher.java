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
        MinecraftCheck();
    }

    // Check to see if Minecraft is installed.
    public static void MinecraftCheck() throws IOException, ZipException {

        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");
        String minecrunchDir = null;
        String minecraftDir = null;

        if (os.contains("Windows")) {
            minecrunchDir = home + "\\.minecrunch\\";
            minecraftDir = home + "\\AppData\\Roaming\\.minecraft\\";
        }

        if (os.contains("Linux")) {
            minecrunchDir = home + "/.minecrunch/";
            minecraftDir = home + "/.minecraft/";
        }

        if (os.contains("Mac")) {
            minecrunchDir = home + "/.minecrunch/";
            minecraftDir = home + "/Library/Application Support/minecraft/";
        }

        File minecraft = new File(minecraftDir + "launcher.jar");
        if (minecraft.exists()) {
            System.out.println("Minecraft is installed.");
            PackUI pui = new PackUI();
            pui.setVisible(true);
        } else {
            System.out.println("Minecraft is not installed.");
            Object[] options = {"Yes please.", "No thanks."};
            Component frame = null;

            // Get answer if the user wants to update or not
            int n = JOptionPane.showOptionDialog(frame,
                    "Would you like to install it now?",
                    "Minecraft is not installed.",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (n == JOptionPane.YES_OPTION) {
                // If yes, download and run Minecraft.jar
                URL mine = new URL("http://s3.amazonaws.com/Minecraft.Download/launcher/Minecraft.jar");
                File fmine = new File(minecrunchDir + "Minecraft.jar");
                FileUtils.copyURLToFile(mine, fmine);

                Component frame2 = null;
                JOptionPane.showMessageDialog(frame2, "Downloaded to: " + minecrunchDir + "Minecraft.jar");

                try {
                    Process proc = Runtime.getRuntime().exec("java -jar " + minecrunchDir + "Minecraft.jar");
                } catch (IOException ex) {
                    Logger.getLogger(Minecrunch_launcher.class.getName()).log(Level.SEVERE, null, ex);
                }

                PackUI pui = new PackUI();
                pui.setVisible(true);

            } else {
                // If user chooses no then just run the PackUI
                PackUI pui = new PackUI();
                pui.setVisible(true);
            }
        }
    }
}