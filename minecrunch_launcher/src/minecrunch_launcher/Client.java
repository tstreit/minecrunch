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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
    String java;
    String minecrunchDir = null;
    String minecraftDir = null;
    String tempDir = null;
    String clientInstall = null;
    String modinstallDir = null;

    Client(Object n, Object m) {
        name = n.toString();
        gamename = m.toString();
    }

    public void Modprofile() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        if (os.contains("Windows")) {
            minecrunchDir = home + "\\.minecrunch\\resources\\";
        } else {
            minecrunchDir = home + "/.minecrunch/resources/";
        }

        // Reading selected modpack profile and getting parameters
        DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = Factory.newDocumentBuilder();
        Document doc = builder.parse(minecrunchDir + name + "_profile.xml");
        doc.getDocumentElement().normalize();

        NodeList nodes = doc.getElementsByTagName("profile");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if ((node.getNodeType() == Node.ELEMENT_NODE)) {
                Element element = (Element) node;
                version = element.getElementsByTagName("version").item(0).getTextContent();
                System.out.println("Forge version: " + version);
                java = element.getElementsByTagName("java").item(0).getTextContent();
                System.out.println("JVM Argument: " + java);
            }
        }
    }

    public void run() {
        // Reading modpack profile XML file and getting profile parameters
        if (os.contains("Windows")) {
            minecraftDir = home + "\\AppData\\Roaming\\.minecraft\\";
            tempDir = home + "\\AppData\\temp\\";
            clientInstall = tempDir + "client_install\\";
            modinstallDir = home + "\\AppData\\Roaming\\.minecraft\\minecrunch\\";
        }

        if (os.contains("Linux")) {
            minecraftDir = home + "/.minecraft/";
            tempDir = home + "/temp/";
            clientInstall = tempDir + "client_install/";
            modinstallDir = home + "/.minecraft/minecrunch/";
        }

        if (os.contains("Mac")) {
            minecraftDir = home + "/Library/Application Support/minecraft/";
            tempDir = home + "/temp/";
            clientInstall = tempDir + "client_install/";
            modinstallDir = home + "/Library/Application Support/minecraft/minecrunch/";
        }

        try {
            Modprofile();
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Install selected modpack
        wd.setVisible(true);
        System.out.println("Modpack picked: " + name);
        System.out.println("Gamename is: " + gamename);
        File dir = new File(tempDir);
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
        File file = new File(tempDir + "client_install.zip");
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        try {
            ZipFile zipFile = new ZipFile(tempDir + "client_install.zip");
            zipFile.extractAll(tempDir);
        } catch (ZipException e) {
        }
        file.delete();

        File newlib = new File(clientInstall + "libraries");
        File oldlib = new File(minecraftDir + "libraries");
        try {
            FileUtils.copyDirectory(newlib, oldlib);
            System.out.println("Copied libraries directory.");
            FileUtils.deleteDirectory(newlib);
            System.out.println("Deleted libraries directory.");
        } catch (IOException ex) {
            System.out.println(ex);
        }

        File newver = new File(clientInstall + "versions");
        File oldver = new File(minecraftDir + "versions");
        try {
            FileUtils.copyDirectory(newver, oldver);
            System.out.println("Copied version directory.");
            FileUtils.deleteDirectory(newver);
            System.out.println("Deleted version directory.");
        } catch (IOException ex) {
            System.out.println(ex);
        }

        File newmods = new File(clientInstall);
        File oldmods = new File(modinstallDir + name + "\\");
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
        String gamedir = modinstallDir + name;
        String filePath = minecraftDir + "launcher_profiles.json";
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader(filePath));
        } catch (IOException | ParseException ex) {
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