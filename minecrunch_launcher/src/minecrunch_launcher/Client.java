/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minecrunch_launcher;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tstreit
 */
public class Client implements Runnable {

    String os = System.getProperty("os.name");
    String home = System.getProperty("user.home");
    WaitDialog wd = new WaitDialog();
    String name;
    String gamename;
    String version;

    Client(Object n, Object m) {
        name = n.toString();
        gamename = m.toString();
    }

    public void run() {
        // Install selected modpack
        // Windows
        if (os.contains("Windows")) {
            wd.setVisible(true);
            System.out.println("Modpack picked: " + name);
            System.out.println("Gamename is: " + gamename);
            File dir = new File(home + "\\AppData\\temp");
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    String console = "Temporary directory created.";
                    System.out.println(console);
                } else {
                    String console = "Temporary directory already exists.";
                    System.out.println(console);
                }
            }
            URL url = null;
            try {
                url = new URL("http://www.minecrunch.net/download/" + name + "/client_install.zip");
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }
            File file = new File(home + "\\AppData\\temp\\client_install.zip");
            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            try {
                ZipFile zipFile = new ZipFile(home + "\\AppData\\temp\\client_install.zip");
                zipFile.extractAll(home + "\\AppData\\temp");
            } catch (ZipException e) {
            }
            file.delete();

            File newlib = new File(home + "\\AppData\\temp\\client_install\\libraries");
            File oldlib = new File(home + "\\AppData\\Roaming\\.minecraft\\libraries");
            try {
                FileUtils.copyDirectory(newlib, oldlib);
                System.out.println("Copied libraries directory.");
                FileUtils.deleteDirectory(newlib);
                System.out.println("Deleted libraries directory.");
            } catch (IOException ex) {
                System.out.println(ex);
            }

            File newver = new File(home + "\\AppData\\temp\\client_install\\versions");
            File oldver = new File(home + "\\AppData\\Roaming\\.minecraft\\versions");
            try {
                FileUtils.copyDirectory(newver, oldver);
                System.out.println("Copied version directory.");
                FileUtils.deleteDirectory(newver);
                System.out.println("Deleted version directory.");
            } catch (IOException ex) {
                System.out.println(ex);
            }

            File newmods = new File(home + "\\AppData\\temp\\client_install\\");
            File oldmods = new File(home + "\\AppData\\Roaming\\.minecraft\\minecrunch\\" + name + "\\");
            try {
                FileUtils.copyDirectory(newmods, oldmods);
                System.out.println("Copied all other directories.");
            } catch (IOException ex) {
                System.out.println(ex);
            }

            try {
                // delete temporary directory
                FileUtils.deleteDirectory(dir);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // Setup profile
            String profile = gamename;
            if (name.contains("cyberpunk")) {
                version = "1.10.2-forge1.10.2-12.18.3.2185";
            } else {
                version = "1.7.10-Forge10.13.4.1558-1.7.10";
            }
            String gamedir = home + "\\AppData\\Roaming\\.minecraft\\minecrunch\\" + name;
            String filePath = home + "\\AppData\\Roaming\\.minecraft\\launcher_profiles.json";
            String java = "-Xmx4G -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
            JSONParser parser = new JSONParser();
            Object obj = null;
            try {
                obj = parser.parse(new FileReader(filePath));
            } catch (IOException | ParseException ex) {
                System.out.println(ex);
            }
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject profiles = (JSONObject) jsonObject.get("profiles");
            String selectedProfile = profile;
            String clientToken = (String) jsonObject.get("clientToken");
            JSONObject authenticationDatabase = (JSONObject) jsonObject.get("authenticationDatabase");
            JSONObject params = new JSONObject();
            params.put("name", profile);
            params.put("gameDir", gamedir);
            params.put("lastVersionId", version);
            params.put("javaArgs", java);
            profiles.put(profile, params);
            JSONObject update = new JSONObject();
            update.put("profiles", profiles);
            update.put("selectedProfile", selectedProfile);
            update.put("clientToken", clientToken);
            update.put("authenticationDatabase", authenticationDatabase);
            try (FileWriter newfile = new FileWriter(filePath)) {
                newfile.write(update.toJSONString());
                newfile.flush();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            wd.setVisible(false);
        }

        // Linux
        if (os.contains("Linux")) {
            wd.setVisible(true);
            System.out.println("Modpack picked: " + name);
            System.out.println("Gamename is: " + gamename);
            File dir = new File(home + "/temp");
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    String console = "Temporary directory created.";
                    System.out.println(console);
                } else {
                    String console = "Temporary directory already exists.";
                    System.out.println(console);
                }
            }
            URL url = null;
            try {
                url = new URL("http://www.minecrunch.net/download/" + name + "/client_install.zip");
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }
            File file = new File(home + "/temp/client_install.zip");
            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            try {
                ZipFile zipFile = new ZipFile(home + "/temp/client_install.zip");
                zipFile.extractAll(home + "/temp");
            } catch (ZipException e) {
            }
            file.delete();

            File newlib = new File(home + "/temp/client/libraries");
            File oldlib = new File(home + "/.minecraft/libraries");
            try {
                FileUtils.copyDirectory(newlib, oldlib);
                System.out.println("Copied libraries directory.");
                FileUtils.deleteDirectory(newlib);
                System.out.println("Deleted libraries directory.");
            } catch (IOException ex) {
                System.out.println(ex);
            }

            File newver = new File(home + "/temp/client/versions");
            File oldver = new File(home + "/.minecraft/versions");
            try {
                FileUtils.copyDirectory(newver, oldver);
                System.out.println("Copied version directory.");
                FileUtils.deleteDirectory(newver);
                System.out.println("Deleted version directory.");
            } catch (IOException ex) {
                System.out.println(ex);
            }

            File newmods = new File(home + "/temp/client/");
            File oldmods = new File(home + "/.minecraft/minecrunch/" + name + "/");
            try {
                FileUtils.copyDirectory(newmods, oldmods);
                System.out.println("Copied all other directories.");
            } catch (IOException ex) {
                System.out.println(ex);
            }

            try {
                // delete temporary directory
                FileUtils.deleteDirectory(dir);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // Setup profile
            String profile = gamename;
            if (name.contains("cyberpunk")) {
                version = "1.10.2-forge1.10.2-12.18.3.2185";
            } else {
                version = "1.7.10-Forge10.13.4.1558-1.7.10";
            }
            String gamedir = home + "/.minecraft/minecrunch/" + name;
            String filePath = home + "/.minecraft/launcher_profiles.json";
            String java = "-Xmx4G -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
            JSONParser parser = new JSONParser();
            Object obj = null;
            try {
                obj = parser.parse(new FileReader(filePath));
            } catch (IOException | ParseException ex) {
                System.out.println(ex);
            }
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject profiles = (JSONObject) jsonObject.get("profiles");
            String selectedProfile = profile;
            String clientToken = (String) jsonObject.get("clientToken");
            JSONObject authenticationDatabase = (JSONObject) jsonObject.get("authenticationDatabase");
            JSONObject params = new JSONObject();
            params.put("name", profile);
            params.put("gameDir", gamedir);
            params.put("lastVersionId", version);
            params.put("javaArgs", java);
            profiles.put(profile, params);
            JSONObject update = new JSONObject();
            update.put("profiles", profiles);
            update.put("selectedProfile", selectedProfile);
            update.put("clientToken", clientToken);
            update.put("authenticationDatabase", authenticationDatabase);
            try (FileWriter newfile = new FileWriter(filePath)) {
                newfile.write(update.toJSONString());
                newfile.flush();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            wd.setVisible(false);
        }

        // Mac
        if (os.contains("Mac")) {
            wd.setVisible(true);
            System.out.println("Modpack picked: " + name);
            System.out.println("Gamename is: " + gamename);
            File dir = new File(home + "/temp");
            if (!dir.exists()) {
                if (dir.mkdir()) {
                    String console = "Temporary directory created.";
                    System.out.println(console);
                } else {
                    String console = "Temporary directory already exists.";
                    System.out.println(console);
                }
            }
            URL url = null;
            try {
                url = new URL("http://www.minecrunch.net/download/" + name + "/client_install.zip");
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }
            File file = new File(home + "/temp/client_install.zip");
            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            try {
                ZipFile zipFile = new ZipFile(home + "/temp/client_install.zip");
                zipFile.extractAll(home + "/temp");
            } catch (ZipException e) {
            }
            file.delete();

            File newlib = new File(home + "/temp/client/libraries");
            File oldlib = new File(home + "/Library/Application Support/minecraft/libraries");
            try {
                FileUtils.copyDirectory(newlib, oldlib);
                System.out.println("Copied libraries directory.");
                FileUtils.deleteDirectory(newlib);
                System.out.println("Deleted libraries directory.");
            } catch (IOException ex) {
                System.out.println(ex);
            }

            File newver = new File(home + "/temp/client/versions");
            File oldver = new File(home + "/Library/Application Support/minecraft/versions");
            try {
                FileUtils.copyDirectory(newver, oldver);
                System.out.println("Copied version directory.");
                FileUtils.deleteDirectory(newver);
                System.out.println("Deleted version directory.");
            } catch (IOException ex) {
                System.out.println(ex);
            }

            File newmods = new File(home + "/temp/client/");
            File oldmods = new File(home + "/Library/Application Support/minecraft/minecrunch/" + name + "/");
            try {
                FileUtils.copyDirectory(newmods, oldmods);
                System.out.println("Copied all other directories.");
            } catch (IOException ex) {
                System.out.println(ex);
            }

            try {
                // delete temporary directory
                FileUtils.deleteDirectory(dir);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // Setup profile
            String profile = gamename;
            if (name.contains("cyberpunk")) {
                version = "1.10.2-forge1.10.2-12.18.3.2185";
            } else {
                version = "1.7.10-Forge10.13.4.1558-1.7.10";
            }
            String gamedir = home + "/Library/Application Support/minecraft/minecrunch/" + name;
            String filePath = home + "/Library/Application Support/minecraft/launcher_profiles.json";
            String java = "-Xmx4G -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
            JSONParser parser = new JSONParser();
            Object obj = null;
            try {
                obj = parser.parse(new FileReader(filePath));
            } catch (IOException | ParseException ex) {
                System.out.println(ex);
            }
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject profiles = (JSONObject) jsonObject.get("profiles");
            String selectedProfile = profile;
            String clientToken = (String) jsonObject.get("clientToken");
            JSONObject authenticationDatabase = (JSONObject) jsonObject.get("authenticationDatabase");
            JSONObject params = new JSONObject();
            params.put("name", profile);
            params.put("gameDir", gamedir);
            params.put("lastVersionId", version);
            params.put("javaArgs", java);
            profiles.put(profile, params);
            JSONObject update = new JSONObject();
            update.put("profiles", profiles);
            update.put("selectedProfile", selectedProfile);
            update.put("clientToken", clientToken);
            update.put("authenticationDatabase", authenticationDatabase);
            try (FileWriter newfile = new FileWriter(filePath)) {
                newfile.write(update.toJSONString());
                newfile.flush();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            wd.setVisible(false);
        }
    }
}