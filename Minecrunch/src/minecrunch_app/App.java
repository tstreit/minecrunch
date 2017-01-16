/*
 * Copyright (c) 2016, tstreit
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
package minecrunch_app;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author tstreit
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        if (Utility.HasConnectivity()) {
            try {
                // Run CheckDirectory method
                CheckDirectory();
            } catch (MalformedURLException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Run();
        }
    }

    private static void CheckDirectory() throws MalformedURLException, IOException {

        // Get system properties
        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");
        String dir;
        String newlib = null;
        String newres = null;

        if (os.contains("Windows")) {
            dir = home + "\\.minecrunch\\";
            newlib = dir + "\\lib\\";
            newres = dir + "\\resources\\";
        } else {
            dir = home + "/.minecrunch/";
            newlib = dir + "/lib/";
            newres = dir + "/resources/";
        }

        // Check for .minecrunch directory if it doesn't exist create main directory, sub-directories, then download libraries and resources
        File minecrunch = new File(dir);
        File lib = new File(newlib);
        File res = new File(newres);

        if (minecrunch.exists()) {
            Update();
        } else {

            // Create directory structure
            minecrunch.mkdir();
            System.out.println("Created: " + minecrunch);
            lib.mkdir();
            System.out.println("Created: " + lib);
            res.mkdir();
            System.out.println("Created: " + res);

            URL lib1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/commons-io-2.4.jar");
            URL lib2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/json-simple-1.1.1.jar");
            URL lib3 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/zip4j_1.3.2.jar");
            URL jar1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_updater.jar");
            URL jar2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_launcher.jar");

            File flib1 = new File(newlib + "commons-io-2.4.jar");
            File flib2 = new File(newlib + "json-simple-1.1.1.jar");
            File flib3 = new File(newlib + "zip4j_1.3.2.jar");
            File fjar1 = new File(dir + "minecrunch_updater.jar");
            File fjar2 = new File(dir + "minecrunch_launcher.jar");

            FileUtils.copyURLToFile(lib1, flib1);
            FileUtils.copyURLToFile(lib2, flib2);
            FileUtils.copyURLToFile(lib3, flib3);
            FileUtils.copyURLToFile(jar1, fjar1);
            FileUtils.copyURLToFile(jar2, fjar2);

            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Install complete.");

            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + dir + "minecrunch_launcher.jar");
                System.exit(0);
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Update Minecrunch Modpack
    public static void Update() {
        // Get system properties
        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");
        String dir;

        if (os.contains("Windows")) {
            dir = home + "\\.minecrunch\\";
        } else {
            dir = home + "/.minecrunch/";
        }

        // Run minecrunch_updater jar
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + dir + "minecrunch_updater.jar");
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public static void Run() {
        // Get system properties
        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");
        String dir;

        if (os.contains("Windows")) {
            dir = home + "\\.minecrunch\\";
        } else {
            dir = home + "/.minecrunch/";
        }

        // Run minecrunch_launcher jar
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + dir + "minecrunch_launcher.jar");
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}