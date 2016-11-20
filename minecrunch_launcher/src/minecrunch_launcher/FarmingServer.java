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
package minecrunch_launcher;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author tstreit
 */
public class FarmingServer implements Runnable {

    String os = System.getProperty("os.name");
    String home = System.getProperty("user.home");
    WaitDialog wd = new WaitDialog();
    
    public void run() {
        wd.setVisible(true);
        // If Windows
        if (os.contains("Windows")) {
            // create directory in users home folder farming_server
            File dir = new File(home + "\\minecrunch\\farming_server");
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    String console = "Temporary directory created.";
                    System.out.println(console);
                } else {
                    String console = "Temporary directory already exists.";
                    System.out.println(console);
                }
            }
            // download file from server
            URL url = null;
            try {
                url = new URL("http://www.minecrunch.net/download/farming/server_install.zip");
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }
            File file = new File(home + "\\server_install.zip");
            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // unzip file in users home folder and extract it to farming_server
            try {
                ZipFile zipFile = new ZipFile(home + "\\server_install.zip");
                zipFile.extractAll(home + "\\minecrunch\\farming_server");
            } catch (ZipException e) {
            }
            File dir2 = new File(home + "\\minecrunch\\farming_server\\server_install");
            File dir3 = new File(home + "\\minecrunch\\farming_server");
            try {
                FileUtils.copyDirectory(dir2, dir3);
                FileUtils.deleteDirectory(dir2);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // clean up and delete zip file that was downloaded
            file.delete();
            wd.setVisible(false);
        }
        
        // If Linux
        if (os.contains("Linux")) {
            // create directory in users home folder farming_server
            File dir = new File(home + "/minecrunch/farming_server");
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    System.out.println("Directory created.");
                } else {
                    System.out.println("Directory already exists.");
                }
            }
            // download file from server
            URL url = null;
            try {
                url = new URL("http://www.minecrunch.net/download/farming/server_install.zip");
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }
            File file = new File(home + "/server_install.zip");
            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // unzip file in users home folder and extract it to farming_server
            try {
                ZipFile zipFile = new ZipFile(home + "/server_install.zip");
                zipFile.extractAll(home + "/minecrunch/farming_server");
            } catch (ZipException e) {
                e.printStackTrace();
            }
            File dir2 = new File(home + "/minecrunch/farming_server/server_install");
            File dir3 = new File(home + "/minecrunch/farming_server");
            try {
                FileUtils.copyDirectory(dir2, dir3);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // clean up and delete zip file that was downloaded
            file.delete();
            wd.setVisible(false);
        }
        
        // If Mac
        if (os.contains("Mac")) {
            // create directory in users home folder farming_server
            File dir = new File(home + "/minecrunch/farming_server");
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    System.out.println("Directory created.");
                } else {
                    System.out.println("Directory already exists.");
                }
            }
            // download file from server
            URL url = null;
            try {
                url = new URL("http://www.minecrunch.net/download/farming/server_install.zip");
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }
            File file = new File(home + "/server_install.zip");
            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // unzip file in users home folder and extract it to farming_server
            try {
                ZipFile zipFile = new ZipFile(home + "/server_install.zip");
                zipFile.extractAll(home + "/minecrunch/farming_server");
            } catch (ZipException e) {
                e.printStackTrace();
            }
            File dir2 = new File(home + "/minecrunch/farming_server/server_install");
            File dir3 = new File(home + "/minecrunch/farming_server");
            try {
                FileUtils.copyDirectory(dir2, dir3);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // clean up and delete zip file that was downloaded
            file.delete();
            wd.setVisible(false);
        }
    }
}