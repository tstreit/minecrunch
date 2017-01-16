/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class Server implements Runnable {

    String os = System.getProperty("os.name");
    String home = System.getProperty("user.home");
    WaitDialog wd = new WaitDialog();
    String name;
    String minecrunchDir = null;

    Server(Object n) {
        name = n.toString();
    }

    public void run() {
        // Install selected server
        if (os.contains("Windows")) {
            minecrunchDir = home + "\\minecrunch\\";
        } else {
            minecrunchDir = home + "/minecrunch/";
        }
        wd.setVisible(true);

        // create directory in users home folder server
        File dir = new File(minecrunchDir + name + "_server");
        if (!dir.exists()) {
            if (dir.mkdir()) {
                String console = "Server directory created.";
                System.out.println(console);
            } else {
                String console = "Server directory already exists.";
                System.out.println(console);
            }
        }
        // download file from server
        URL url = null;
        try {
            url = new URL("http://www.minecrunch.net/download/" + name + "/server_install.zip");
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }
        File file = new File(minecrunchDir + "server_install.zip");
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        // unzip file in users home folder and extract it to server
        try {
            ZipFile zipFile = new ZipFile(minecrunchDir + "server_install.zip");
            zipFile.extractAll(minecrunchDir + name + "_server");
        } catch (ZipException e) {
        }
        File dir2 = new File(minecrunchDir + name + "_server\\server_install");
        File dir3 = new File(minecrunchDir + name + "_server");
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
}