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

        try {
            // Run CheckDirectory method
            CheckDirectory();
        } catch (MalformedURLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void CheckDirectory() throws MalformedURLException, IOException {

        // Get system properties
        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");

        // Check for .minecrunch directory if it doesn't exist create main directory, sub-directories, then download libraries and resources 
        if (os.contains("Windows")) {
            File minecrunchDir = new File(home + "\\.minecrunch");
            File libDir = new File(home + "\\.minecrunch\\lib");
            File resDir = new File(home + "\\.minecrunch\\resources");

            if (minecrunchDir.exists()) {
                Update();
            } else {

                // Create directory structure
                minecrunchDir.mkdir();
                System.out.println("Created: " + minecrunchDir);
                libDir.mkdir();
                System.out.println("Created: " + libDir);
                resDir.mkdir();
                System.out.println("Created: " + resDir);

                URL lib1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/commons-io-2.4.jar");
                URL lib2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/json-simple-1.1.1.jar");
                URL lib3 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/zip4j_1.3.2.jar");
                URL res1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/medieval_client.txt");
                URL res2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/medieval_server_details.txt");
                URL res3 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/techno_client.txt");
                URL res4 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/techno_server_details.txt");
                URL res5 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/medieval.jpg");
                URL res6 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/techno.jpg");
                URL jar1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_updater.jar");
                URL jar2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_launcher.jar");
                URL ver1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/version.txt");

                File flib1 = new File(home + "\\.minecrunch\\lib\\commons-io-2.4.jar");
                File flib2 = new File(home + "\\.minecrunch\\lib\\json-simple-1.1.1.jar");
                File flib3 = new File(home + "\\.minecrunch\\lib\\zip4j_1.3.2.jar");
                File fres1 = new File(home + "\\.minecrunch\\resources\\medieval_client.txt");
                File fres2 = new File(home + "\\.minecrunch\\resources\\medieval_server_details.txt");
                File fres3 = new File(home + "\\.minecrunch\\resources\\techno_client.txt");
                File fres4 = new File(home + "\\.minecrunch\\resources\\techno_server_details.txt");
                File fres5 = new File(home + "\\.minecrunch\\resources\\medieval.jpg");
                File fres6 = new File(home + "\\.minecrunch\\resources\\techno.jpg");
                File fjar1 = new File(home + "\\.minecrunch\\minecrunch_updater.jar");
                File fjar2 = new File(home + "\\.minecrunch\\minecrunch_launcher.jar");
                File fver1 = new File(home + "\\.minecrunch\\resources\\version.txt");

                FileUtils.copyURLToFile(lib1, flib1);
                FileUtils.copyURLToFile(lib2, flib2);
                FileUtils.copyURLToFile(lib3, flib3);
                FileUtils.copyURLToFile(res1, fres1);
                FileUtils.copyURLToFile(res2, fres2);
                FileUtils.copyURLToFile(res3, fres3);
                FileUtils.copyURLToFile(res4, fres4);
                FileUtils.copyURLToFile(res5, fres5);
                FileUtils.copyURLToFile(res6, fres6);
                FileUtils.copyURLToFile(jar1, fjar1);
                FileUtils.copyURLToFile(jar2, fjar2);
                FileUtils.copyURLToFile(ver1, fver1);

                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Install complete.");

                try {
                    Process proc = Runtime.getRuntime().exec("java -jar " + home + "\\.minecrunch\\minecrunch_launcher.jar");
                    System.exit(0);
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (os.contains("Linux")) {
            File minecrunchDir = new File(home + "/.minecrunch");
            File libDir = new File(home + "/.minecrunch/lib");
            File resDir = new File(home + "/.minecrunch/resources");

            if (minecrunchDir.exists()) {
                Update();
            } else {

                // Create directory structure
                minecrunchDir.mkdir();
                System.out.println("Created: " + minecrunchDir);
                libDir.mkdir();
                System.out.println("Created: " + libDir);
                resDir.mkdir();
                System.out.println("Created: " + resDir);

                URL lib1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/commons-io-2.4.jar");
                URL lib2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/json-simple-1.1.1.jar");
                URL lib3 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/zip4j_1.3.2.jar");
                URL res1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/medieval_client.txt");
                URL res2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/medieval_server_details.txt");
                URL res3 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/techno_client.txt");
                URL res4 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/techno_server_details.txt");
                URL res5 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/medieval.jpg");
                URL res6 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/techno.jpg");
                URL jar1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_updater.jar");
                URL jar2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_launcher.jar");
                URL ver1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/version.txt");

                File flib1 = new File(home + "/.minecrunch/lib/commons-io-2.4.jar");
                File flib2 = new File(home + "/.minecrunch/lib/json-simple-1.1.1.jar");
                File flib3 = new File(home + "/.minecrunch/lib/zip4j_1.3.2.jar");
                File fres1 = new File(home + "/.minecrunch/resources/medieval_client.txt");
                File fres2 = new File(home + "/.minecrunch/resources/medieval_server_details.txt");
                File fres3 = new File(home + "/.minecrunch/resources/techno_client.txt");
                File fres4 = new File(home + "/.minecrunch/resources/techno_server_details.txt");
                File fres5 = new File(home + "/.minecrunch/resources/medieval.jpg");
                File fres6 = new File(home + "/.minecrunch/resources/techno.jpg");
                File fjar1 = new File(home + "/.minecrunch/minecrunch_updater.jar");
                File fjar2 = new File(home + "/.minecrunch/minecrunch_launcher.jar");
                File fver1 = new File(home + "/.minecrunch/resources/version.txt");

                FileUtils.copyURLToFile(lib1, flib1);
                FileUtils.copyURLToFile(lib2, flib2);
                FileUtils.copyURLToFile(lib3, flib3);
                FileUtils.copyURLToFile(res1, fres1);
                FileUtils.copyURLToFile(res2, fres2);
                FileUtils.copyURLToFile(res3, fres3);
                FileUtils.copyURLToFile(res4, fres4);
                FileUtils.copyURLToFile(res5, fres5);
                FileUtils.copyURLToFile(res6, fres6);
                FileUtils.copyURLToFile(jar1, fjar1);
                FileUtils.copyURLToFile(jar2, fjar2);
                FileUtils.copyURLToFile(ver1, fver1);

                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Install complete.");

                try {
                    Process proc = Runtime.getRuntime().exec("java -jar " + home + "/.minecrunch/minecrunch_launcher.jar");
                    System.exit(0);
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (os.contains("Mac")) {
            File minecrunchDir = new File(home + "/.minecrunch");
            File libDir = new File(home + "/.minecrunch/lib");
            File resDir = new File(home + "/.minecrunch/resources");

            if (minecrunchDir.exists()) {
                Update();
            } else {

                // Create directory structure
                minecrunchDir.mkdir();
                System.out.println("Created: " + minecrunchDir);
                libDir.mkdir();
                System.out.println("Created: " + libDir);
                resDir.mkdir();
                System.out.println("Created: " + resDir);

                URL lib1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/commons-io-2.4.jar");
                URL lib2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/json-simple-1.1.1.jar");
                URL lib3 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/zip4j_1.3.2.jar");
                URL res1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/medieval_client.txt");
                URL res2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/medieval_server_details.txt");
                URL res3 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/techno_client.txt");
                URL res4 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/techno_server_details.txt");
                URL res5 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/medieval.jpg");
                URL res6 = new URL("http://www.minecrunch.net/download/minecrunch_installer/resources/techno.jpg");
                URL jar1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_updater.jar");
                URL jar2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_launcher.jar");
                URL ver1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/version.txt");

                File flib1 = new File(home + "/.minecrunch/lib/commons-io-2.4.jar");
                File flib2 = new File(home + "/.minecrunch/lib/json-simple-1.1.1.jar");
                File flib3 = new File(home + "/.minecrunch/lib/zip4j_1.3.2.jar");
                File fres1 = new File(home + "/.minecrunch/resources/medieval_client.txt");
                File fres2 = new File(home + "/.minecrunch/resources/medieval_server_details.txt");
                File fres3 = new File(home + "/.minecrunch/resources/techno_client.txt");
                File fres4 = new File(home + "/.minecrunch/resources/techno_server_details.txt");
                File fres5 = new File(home + "/.minecrunch/resources/medieval.jpg");
                File fres6 = new File(home + "/.minecrunch/resources/techno.jpg");
                File fjar1 = new File(home + "/.minecrunch/minecrunch_updater.jar");
                File fjar2 = new File(home + "/.minecrunch/minecrunch_launcher.jar");
                File fver1 = new File(home + "/.minecrunch/resources/version.txt");

                FileUtils.copyURLToFile(lib1, flib1);
                FileUtils.copyURLToFile(lib2, flib2);
                FileUtils.copyURLToFile(lib3, flib3);
                FileUtils.copyURLToFile(res1, fres1);
                FileUtils.copyURLToFile(res2, fres2);
                FileUtils.copyURLToFile(res3, fres3);
                FileUtils.copyURLToFile(res4, fres4);
                FileUtils.copyURLToFile(res5, fres5);
                FileUtils.copyURLToFile(res6, fres6);
                FileUtils.copyURLToFile(jar1, fjar1);
                FileUtils.copyURLToFile(jar2, fjar2);
                FileUtils.copyURLToFile(ver1, fver1);

                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Install complete.");

                try {
                    Process proc = Runtime.getRuntime().exec("java -jar " + home + "/.minecrunch/minecrunch_launcher.jar");
                    System.exit(0);
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Update Minecrunch Modpack
    public static void Update() {
        // Get system properties
        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");

        // Run minecrunch_updater jar
        if (os.contains("Windows")) {
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "\\.minecrunch\\minecrunch_updater.jar");
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (os.contains("Linux")) {
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "/.minecrunch/minecrunch_updater.jar");
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (os.contains("Mac")) {
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "/.minecrunch/minecrunch_updater.jar");
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}