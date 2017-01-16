/*
 * Copyright (c) 2017, tstreit
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package minecrunch_launcher;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author tstreit
 */
public class Utility {

    private static final String URL = "www.minecrunch.net";
    private static final int TIMEOUT = 2000;

    public static boolean HasConnectivity() {
        boolean isConnected = false;
        // Test for internet connection.
        try {
            InetAddress address = InetAddress.getByName(URL);
            if (address.isReachable(TIMEOUT)) {
                System.out.println("Minecrunch is connected.");
                isConnected = true;
            }
        } catch (Exception e) {
        }
        return isConnected;
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

    public static void GetResources() throws MalformedURLException, IOException, ZipException {
        // Get system properties
        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");
        String minecrunchDir = null;
        // Download all resources
        if (os.contains("Windows")) {
            minecrunchDir = home + "\\.minecrunch\\resources\\";
        } else {
            minecrunchDir = home + "/.minecrunch/resources/";
        }
        // Download res.zip
        URL res = new URL("http://www.minecrunch.net/download/res.zip");
        File fres = new File(minecrunchDir + "res.zip");
        FileUtils.copyURLToFile(res, fres);

        // Unzip file to .minecrunch/resources directory
        ZipFile reszip = new ZipFile(minecrunchDir + "res.zip");
        reszip.extractAll(minecrunchDir);

        // delete res.zip file
        fres.delete();
    }
}
