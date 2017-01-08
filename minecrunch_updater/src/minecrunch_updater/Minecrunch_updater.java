/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecrunch_updater;

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
public class Minecrunch_updater {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            VersionCheck();
        } catch (MalformedURLException ex) {
            Logger.getLogger(Minecrunch_updater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Check to see if there is a newer version of Minecrunch Modpack
    private static void VersionCheck() throws MalformedURLException {

        // Get system properties
        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");

        if (os.contains("Windows")) {
            try {
                // Get version.txt from server
                URL url = new URL("http://www.minecrunch.net/download/minecrunch_installer/version.txt");
                File file = new File(home + "\\.minecrunch\\version.txt");
                File file2 = new File(home + "\\.minecrunch\\resources\\version.txt");

                FileUtils.copyURLToFile(url, file);
                boolean isTwoEqual = FileUtils.contentEquals(file, file2);

                if (isTwoEqual) {
                    System.out.println("Up to date.");
                    Run();
                } else {
                    Object[] options = {"Yes, please",
                        "No way!"};
                    Component frame = null;
                    // Get answer if the user wants to update or not
                    int n = JOptionPane.showOptionDialog(frame,
                            "There is an update to Minecrunch Modpack.",
                            "Would you like to update now?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (n == JOptionPane.YES_OPTION) {
                        // If yes run the Update method
                        Update();
                    } else {
                        // If user chooses no then just run the minecrunch_launcher jar file
                        Run();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (os.contains("Linux")) {
            try {
                // Get version.txt from server
                URL url = new URL("http://www.minecrunch.net/download/minecrunch_installer/version.txt");
                File file = new File(home + "/.minecrunch/version.txt");
                File file2 = new File(home + "/.minecrunch/resources/version.txt");

                FileUtils.copyURLToFile(url, file);
                boolean isTwoEqual = FileUtils.contentEquals(file, file2);

                if (isTwoEqual) {
                    System.out.println("Up to date.");
                    Run();
                } else {
                    Object[] options = {"Yes, please",
                        "No way!"};
                    Component frame = null;
                    // Get answer if the user wants to update or not
                    int n = JOptionPane.showOptionDialog(frame,
                            "There is an update to Minecrunch Modpack.",
                            "Would you like to update now?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (n == JOptionPane.YES_OPTION) {
                        // If yes run the Update method
                        Update();
                    } else {
                        // If user chooses no then just run the minecrunch_launcher jar file
                        Run();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (os.contains("Mac")) {
            try {
                // Get version.txt from server
                URL url = new URL("http://www.minecrunch.net/download/minecrunch_installer/version.txt");
                File file = new File(home + "/.minecrunch/version.txt");
                File file2 = new File(home + "/.minecrunch/resources/version.txt");

                FileUtils.copyURLToFile(url, file);
                boolean isTwoEqual = FileUtils.contentEquals(file, file2);

                if (isTwoEqual) {
                    System.out.println("Up to date.");
                    Run();
                } else {
                    Object[] options = {"Yes, please",
                        "No way!"};
                    Component frame = null;
                    // Get answer if the user wants to update or not
                    int n = JOptionPane.showOptionDialog(frame,
                            "There is an update to Minecrunch Modpack.",
                            "Would you like to update now?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (n == JOptionPane.YES_OPTION) {
                        // If yes run the Update method
                        Update();
                    } else {
                        // If user chooses no then just run the minecrunch_launcher jar file
                        Run();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    // Run update
    private static void Update() throws MalformedURLException, IOException {

        // Get system properties
        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");

        // Check for .minecrunch directory if it doesn't exist create main directory, sub-directories, then download libraries and resources 
        if (os.contains("Windows")) {

            URL lib1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/commons-io-2.4.jar");
            URL lib2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/json-simple-1.1.1.jar");
            URL lib3 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/zip4j_1.3.2.jar");
            URL jar1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_launcher.jar");

            File flib1 = new File(home + "\\.minecrunch\\lib\\commons-io-2.4.jar");
            File flib2 = new File(home + "\\.minecrunch\\lib\\json-simple-1.1.1.jar");
            File flib3 = new File(home + "\\.minecrunch\\lib\\zip4j_1.3.2.jar");
            File fjar1 = new File(home + "\\.minecrunch\\minecrunch_launcher.jar");

            FileUtils.copyURLToFile(lib1, flib1);
            FileUtils.copyURLToFile(lib2, flib2);
            FileUtils.copyURLToFile(lib3, flib3);
            FileUtils.copyURLToFile(jar1, fjar1);

            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Update complete.");

            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "\\.minecrunch\\minecrunch_launcher.jar");
                System.exit(0);
            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (os.contains("Linux")) {

            URL lib1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/commons-io-2.4.jar");
            URL lib2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/json-simple-1.1.1.jar");
            URL lib3 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/zip4j_1.3.2.jar");
            URL jar1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_launcher.jar");

            File flib1 = new File(home + "/.minecrunch/lib/commons-io-2.4.jar");
            File flib2 = new File(home + "/.minecrunch/lib/json-simple-1.1.1.jar");
            File flib3 = new File(home + "/.minecrunch/lib/zip4j_1.3.2.jar");
            File fjar1 = new File(home + "/.minecrunch/minecrunch_launcher.jar");

            FileUtils.copyURLToFile(lib1, flib1);
            FileUtils.copyURLToFile(lib2, flib2);
            FileUtils.copyURLToFile(lib3, flib3);
            FileUtils.copyURLToFile(jar1, fjar1);

            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Update complete.");

            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "/.minecrunch/minecrunch_launcher.jar");
                System.exit(0);
            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (os.contains("Mac")) {

            URL lib1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/commons-io-2.4.jar");
            URL lib2 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/json-simple-1.1.1.jar");
            URL lib3 = new URL("http://www.minecrunch.net/download/minecrunch_installer/lib/zip4j_1.3.2.jar");
            URL jar1 = new URL("http://www.minecrunch.net/download/minecrunch_installer/minecrunch_launcher.jar");

            File flib1 = new File(home + "/.minecrunch/lib/commons-io-2.4.jar");
            File flib2 = new File(home + "/.minecrunch/lib/json-simple-1.1.1.jar");
            File flib3 = new File(home + "/.minecrunch/lib/zip4j_1.3.2.jar");
            File fjar1 = new File(home + "/.minecrunch/minecrunch_launcher.jar");

            FileUtils.copyURLToFile(lib1, flib1);
            FileUtils.copyURLToFile(lib2, flib2);
            FileUtils.copyURLToFile(lib3, flib3);
            FileUtils.copyURLToFile(jar1, fjar1);

            Component frame = null;
            JOptionPane.showMessageDialog(frame, "Update complete.");

            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "/.minecrunch/minecrunch_launcher.jar");
                System.exit(0);
            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Run minecrunch_launcher jar file
    public static void Run() {
        // Get system properties
        String os = System.getProperty("os.name");
        String home = System.getProperty("user.home");

        // If user chooses no then just run the minecrunch_launcher jar file
        if (os.contains("Windows")) {
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "\\.minecrunch\\minecrunch_launcher.jar");
                System.exit(0);
            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (os.contains("Linux")) {
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "/.minecrunch/minecrunch_launcher.jar");
                System.exit(0);
            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (os.contains("Mac")) {
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "/.minecrunch/minecrunch_launcher.jar");
                System.exit(0);
            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}