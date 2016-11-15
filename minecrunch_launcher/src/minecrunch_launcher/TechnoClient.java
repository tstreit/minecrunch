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
public class TechnoClient implements Runnable {

    String os = System.getProperty("os.name");
    String home = System.getProperty("user.home");
    WaitDialog wd = new WaitDialog();

    public void run() {
        wd.setVisible(true);
        // If Windows
        if (os.contains("Windows")) {
            // create temporary directory
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
            // download file from server
            URL url = null;
            try {
                url = new URL("http://www.minecrunch.net/download/techno/client_install.zip");
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }
            File file = new File(home + "\\AppData\\temp\\client_install.zip");
            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            // unzip file in users home folder and extract it to temp
            try {
                ZipFile zipFile = new ZipFile(home + "\\AppData\\temp\\client_install.zip");
                zipFile.extractAll(home + "\\AppData\\temp");
            } catch (ZipException e) {
            }

            // clean up and delete zip file that was downloaded
            file.delete();

            // move folders and files to .minecraft directory
            File newlib = new File(home + "\\AppData\\temp\\client_install\\libraries");
            File oldlib = new File(home + "\\AppData\\Roaming\\.minecraft\\libraries");
            try {
                FileUtils.copyDirectory(newlib, oldlib);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newlib.delete();

            File newmods = new File(home + "\\AppData\\temp\\client_install\\mods");
            File oldmods = new File(home + "\\AppData\\Roaming\\.minecraft\\minecrunch\\techno\\mods");
            try {
                FileUtils.copyDirectory(newmods, oldmods);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newmods.delete();

            File newres = new File(home + "\\AppData\\temp\\client_install\\resourcepacks");
            File oldres = new File(home + "\\AppData\\Roaming\\.minecraft\\minecrunch\\techno\\resourcepacks");
            try {
                FileUtils.copyDirectory(newres, oldres);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newres.delete();

            File newver = new File(home + "\\AppData\\temp\\client_install\\versions");
            File oldver = new File(home + "\\AppData\\Roaming\\.minecraft\\versions");
            try {
                FileUtils.copyDirectory(newver, oldver);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newver.delete();

            File newopt = new File(home + "\\AppData\\temp\\client_install\\options.txt");
            File oldopt = new File(home + "\\AppData\\Roaming\\.minecraft\\minecrunch\\techno\\options.txt");
            try {
                FileUtils.copyFile(newopt, oldopt);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newopt.delete();

            try {
                // delete temporary directory
                FileUtils.deleteDirectory(dir);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // edit .minecraft/launcher_profiles.json
            String profile = "Techno Color Minecrunch";
            String version = "1.7.10-Forge10.13.4.1558-1.7.10";
            String gamedir = home + "\\AppData\\Roaming\\.minecraft\\minecrunch\\techno";
            String filePath = home + "\\AppData\\Roaming\\.minecraft\\launcher_profiles.json";

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

        // If Linux
        if (os.contains("Linux")) {
            // create temporary directory
            File dir = new File(home + "/temp");
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
                url = new URL("http://www.minecrunch.net/download/techno/client_install.zip");
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }
            File file = new File(home + "/temp/client_install.zip");
            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            // unzip file in users home folder and extract it to temp
            try {
                ZipFile zipFile = new ZipFile(home + "/temp/client_install.zip");
                zipFile.extractAll(home + "/temp");
            } catch (ZipException e) {
                e.printStackTrace();
            }

            // clean up and delete zip file that was downloaded
            file.delete();

            // move folders and files to .minecraft directory
            File newlib = new File(home + "/temp/client_install/libraries");
            File oldlib = new File(home + "/.minecraft/libraries");
            try {
                FileUtils.copyDirectory(newlib, oldlib);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newlib.delete();

            File newmods = new File(home + "/temp/client_install/mods");
            File oldmods = new File(home + "/.minecraft/minecrunch/techno/mods");
            try {
                FileUtils.copyDirectory(newmods, oldmods);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newmods.delete();

            File newres = new File(home + "/temp/client_install/resourcepacks");
            File oldres = new File(home + "/.minecraft/minecrunch/techno/resourcepacks");
            try {
                FileUtils.copyDirectory(newres, oldres);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newres.delete();

            File newver = new File(home + "/temp/client_install/versions");
            File oldver = new File(home + "/.minecraft/versions");
            try {
                FileUtils.copyDirectory(newver, oldver);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newver.delete();

            File newopt = new File(home + "/temp/client_install/options.txt");
            File oldopt = new File(home + "/.minecraft//minecrunch/techno/options.txt");
            try {
                FileUtils.copyFile(newopt, oldopt);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newopt.delete();

            try {
                // delete temporary directory
                FileUtils.deleteDirectory(dir);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // edit .minecraft/launcher_profiles.json
            String profile = "Techno Color Minecrunch";
            String version = "1.7.10-Forge10.13.4.1558-1.7.10";
            String gamedir = home + "/.minecraft/minecrunch/techno";
            String filePath = home + "/.minecraft/launcher_profiles.json";

            JSONParser parser = new JSONParser();
            Object obj = null;
            try {
                obj = parser.parse(new FileReader(filePath));
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (ParseException ex) {
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

        // If Mac
        if (os.contains("Mac")) {
            // create temporary directory
            File dir = new File(home + "/temp");
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
                url = new URL("http://www.minecrunch.net/download/techno/client_install.zip");
            } catch (MalformedURLException ex) {
                System.out.println(ex);
            }
            File file = new File(home + "/temp/client_install.zip");
            try {
                FileUtils.copyURLToFile(url, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            // unzip file in users home folder and extract it to temp
            try {
                ZipFile zipFile = new ZipFile(home + "/temp/client_install.zip");
                zipFile.extractAll(home + "/temp");
            } catch (ZipException e) {
                e.printStackTrace();
            }

            // clean up and delete zip file that was downloaded
            file.delete();

            // move folders and files to .minecraft directory
            File newlib = new File(home + "/temp/client_install/libraries");
            File oldlib = new File(home + "/Library/Application Support/minecraft/libraries");
            try {
                FileUtils.copyDirectory(newlib, oldlib);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newlib.delete();

            File newmods = new File(home + "/temp/client_install/mods");
            File oldmods = new File(home + "/Library/Application Support/minecraft/minecrunch/techno/mods");
            try {
                FileUtils.copyDirectory(newmods, oldmods);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newmods.delete();

            File newres = new File(home + "/temp/client_install/resourcepacks");
            File oldres = new File(home + "/Library/Application Support/minecraft/minecrunch/techno/resourcepacks");
            try {
                FileUtils.copyDirectory(newres, oldres);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newres.delete();

            File newver = new File(home + "/temp/client_install/versions");
            File oldver = new File(home + "/Library/Application Support/minecraft/versions");
            try {
                FileUtils.copyDirectory(newver, oldver);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newver.delete();

            File newopt = new File(home + "/temp/client_install/options.txt");
            File oldopt = new File(home + "/Library/Application Support/minecraft/minecrunch/techno/options.txt");
            try {
                FileUtils.copyFile(newopt, oldopt);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            newopt.delete();

            try {
                // delete temporary directory
                FileUtils.deleteDirectory(dir);
            } catch (IOException ex) {
                System.out.println(ex);
            }

            // edit .minecraft/launcher_profiles.json
            String profile = "Techno Color Minecrunch";
            String version = "1.7.10-Forge10.13.4.1558-1.7.10";
            String gamedir = home + "/Library/Application Support/minecraft/minecrunch/techno";
            String filePath = home + "/Library/Application Support/minecraft/launcher_profiles.json";

            JSONParser parser = new JSONParser();
            Object obj = null;
            try {
                obj = parser.parse(new FileReader(filePath));
            } catch (IOException ex) {
                System.out.println(ex);
            } catch (ParseException ex) {
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