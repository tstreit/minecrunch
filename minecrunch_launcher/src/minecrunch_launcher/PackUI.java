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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author tstreit
 */
public class PackUI extends javax.swing.JFrame {

    // Define global variables
    String name;
    String selected;

    // Get system properties
    String os = System.getProperty("os.name");
    String home = System.getProperty("user.home");

    /**
     * Creates new form PackUI
     *
     * @throws java.io.IOException
     * @throws net.lingala.zip4j.exception.ZipException
     */
    public PackUI() throws IOException, ZipException {
        GetResources();
        initComponents();
        PopulateComboBoxes();
        this.setLocationRelativeTo(null);
    }

    // Exit application
    public void Exit() {
        System.exit(0);
    }

    public void GetResources() throws MalformedURLException, IOException, ZipException {
        // Download all resources
        // Windows
        if (os.contains("Windows")) {
            // Download res.zip
            URL res = new URL("http://www.minecrunch.net/download/res.zip");
            File fres = new File(home + "\\.minecrunch\\resources\\res.zip");
            FileUtils.copyURLToFile(res, fres);

            // Unzip file to .minecrunch/resources directory
            ZipFile reszip = new ZipFile(home + "\\.minecrunch\\resources\\res.zip");
            reszip.extractAll(home + "\\.minecrunch\\resources");

            // delete res.zip file
            fres.delete();
        }

        // Linux
        if (os.contains("Linux")) {
            // Download res.zip
            URL res = new URL("http://www.minecrunch.net/download/res.zip");
            File fres = new File(home + "/.minecrunch/resources/res.zip");
            FileUtils.copyURLToFile(res, fres);

            // Unzip file to .minecrunch/resources directory
            ZipFile reszip = new ZipFile(home + "/.minecrunch/resources/res.zip");
            reszip.extractAll(home + "/.minecrunch/resources");

            // delete res.zip file
            fres.delete();
        }

        // Mac
        if (os.contains("Mac")) {
            // Download res.zip
            URL res = new URL("http://www.minecrunch.net/download/res.zip");
            File fres = new File(home + "/.minecrunch/resources/res.zip");
            FileUtils.copyURLToFile(res, fres);

            // Unzip file to .minecrunch/resources directory
            ZipFile reszip = new ZipFile(home + "/.minecrunch/resources/res.zip");
            reszip.extractAll(home + "/.minecrunch/resources");

            // delete res.zip file
            fres.delete();
        }
    }

    public void PopulateComboBoxes() {
        // Read XML modpack.xml and load content into jComboboxes 1 & 2
        try {
            // Windows
            if (os.contains("Windows")) {
                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = Factory.newDocumentBuilder();
                Document doc = builder.parse(home + "\\.minecrunch\\resources\\modpacks.xml");
                //creating an XPathFactory:
                XPathFactory factory = XPathFactory.newInstance();
                //using this factory to create an XPath object:
                XPath xpath = factory.newXPath();

                // XPath Query for showing all nodes value
                XPathExpression expr = xpath.compile("modpacks");
                Object result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                for (int i = 0; i < nodes.getLength(); i++) {

                    Element el = (Element) nodes.item(i);
                    NodeList children = el.getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node child = children.item(k);
                        if (child.getNodeType() != Node.TEXT_NODE) {
                            jComboBox1.addItem(child.getTextContent());
                            jComboBox2.addItem(child.getTextContent());
                        }
                    }
                }
            }

            // Linux
            if (os.contains("Linux")) {
                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = Factory.newDocumentBuilder();
                Document doc = builder.parse(home + "/.minecrunch/resources/modpacks.xml");
                //creating an XPathFactory:
                XPathFactory factory = XPathFactory.newInstance();
                //using this factory to create an XPath object:
                XPath xpath = factory.newXPath();

                // XPath Query for showing all nodes value
                XPathExpression expr = xpath.compile("modpacks");
                Object result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                for (int i = 0; i < nodes.getLength(); i++) {

                    Element el = (Element) nodes.item(i);
                    NodeList children = el.getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node child = children.item(k);
                        if (child.getNodeType() != Node.TEXT_NODE) {
                            jComboBox1.addItem(child.getTextContent());
                            jComboBox2.addItem(child.getTextContent());
                        }
                    }
                }
            }

            // Mac
            if (os.contains("Mac")) {
                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = Factory.newDocumentBuilder();
                Document doc = builder.parse(home + "/.minecrunch/resources/modpacks.xml");
                //creating an XPathFactory:
                XPathFactory factory = XPathFactory.newInstance();
                //using this factory to create an XPath object:
                XPath xpath = factory.newXPath();

                // XPath Query for showing all nodes value
                XPathExpression expr = xpath.compile("modpacks");
                Object result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                for (int i = 0; i < nodes.getLength(); i++) {

                    Element el = (Element) nodes.item(i);
                    NodeList children = el.getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node child = children.item(k);
                        if (child.getNodeType() != Node.TEXT_NODE) {
                            jComboBox1.addItem(child.getTextContent());
                            jComboBox2.addItem(child.getTextContent());
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(PackUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void GetClientNode() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        // Get selected modpack name from jComboBox2
        selected = jComboBox2.getSelectedItem().toString();

        // Windows
        if (os.contains("Windows")) {
            // Read modpacks.xml and compare child node content to string provided by jCombox2
            DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = Factory.newDocumentBuilder();
            Document doc = builder.parse(home + "\\.minecrunch\\resources\\modpacks.xml");

            NodeList nodes = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if ((node.getNodeType() == Node.ELEMENT_NODE)) {
                    Element childElement = (Element) node;
                    if (childElement.getTextContent().contentEquals(selected)) {
                        // Set node name to string name to pass to LoadClientDetails method
                        name = childElement.getNodeName();
                    }
                }
            }
            LoadClientDetails();
        }

        // Linux
        if (os.contains("Linux")) {
            // Read modpacks.xml and compare child node content to string provided by jCombox2
            DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = Factory.newDocumentBuilder();
            Document doc = builder.parse(home + "/.minecrunch/resources/modpacks.xml");

            NodeList nodes = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if ((node.getNodeType() == Node.ELEMENT_NODE)) {
                    Element childElement = (Element) node;
                    if (childElement.getTextContent().contentEquals(selected)) {
                        // Set node name to string name to pass to LoadClientDetails method
                        name = childElement.getNodeName();
                    }
                }
            }
            LoadClientDetails();
        }
        // Mac
        if (os.contains("Mac")) {
            // Read modpacks.xml and compare child node content to string provided by jCombox2
            DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = Factory.newDocumentBuilder();
            Document doc = builder.parse(home + "/.minecrunch/resources/modpacks.xml");

            NodeList nodes = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if ((node.getNodeType() == Node.ELEMENT_NODE)) {
                    Element childElement = (Element) node;
                    if (childElement.getTextContent().contentEquals(selected)) {
                        // Set node name to string name to pass to LoadClientDetails method
                        name = childElement.getNodeName();
                    }
                }
            }
            LoadClientDetails();
        }
    }

    public void LoadClientDetails() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        // With name variable provided read specified xml and get specified jpg files then load those parameters into jLabel2 and jTextArea4
        // Windows
        if (os.contains("Windows")) {
            // Set label and textarea to nothing
            if (name.contains("empty")) {
                ImageIcon imagenone = new ImageIcon();
                jLabel2.setIcon(imagenone);
                jTextArea4.setText("");
            } else {
                // Load label and textarea with content of xml file and jpg
                ImageIcon imagenone = new ImageIcon();
                jLabel2.setIcon(imagenone);
                jTextArea4.setText("");
                ImageIcon imagemc = new ImageIcon(home + "\\.minecrunch\\resources\\" + name + ".jpg");
                jLabel2.setIcon(imagemc);

                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = Factory.newDocumentBuilder();
                Document doc = builder.parse(home + "\\.minecrunch\\resources\\" + name + "_client.xml");

                // creating an XPathFactory:
                XPathFactory factory = XPathFactory.newInstance();
                // using this factory to create an XPath object: 
                XPath xpath = factory.newXPath();

                // XPath Query for showing all nodes value
                XPathExpression expr = xpath.compile("client");
                Object result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element el = (Element) nodes.item(i);
                    NodeList children = el.getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node child = children.item(k);
                        if (child.getNodeType() != Node.TEXT_NODE) {
                            jTextArea4.append(child.getTextContent());
                            jTextArea4.append("\n");
                            jTextArea4.setCaretPosition(0);
                        }
                    }
                }
            }
        }

        // Linux
        if (os.contains("Linux")) {
            // Set label and textarea to nothing
            if (name.contains("empty")) {
                ImageIcon imagenone = new ImageIcon();
                jLabel2.setIcon(imagenone);
                jTextArea4.setText("");
            } else {
                // Load label and textarea with content of xml file and jpg
                ImageIcon imagenone = new ImageIcon();
                jLabel2.setIcon(imagenone);
                jTextArea4.setText("");

                ImageIcon imagemc = new ImageIcon(home + "/.minecrunch/resources/" + name + ".jpg");
                jLabel2.setIcon(imagemc);

                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = Factory.newDocumentBuilder();
                Document doc = builder.parse(home + "/.minecrunch/resources/" + name + "_client.xml");

                // creating an XPathFactory:
                XPathFactory factory = XPathFactory.newInstance();
                // using this factory to create an XPath object: 
                XPath xpath = factory.newXPath();

                // XPath Query for showing all nodes value
                XPathExpression expr = xpath.compile("client");
                Object result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element el = (Element) nodes.item(i);
                    NodeList children = el.getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node child = children.item(k);
                        if (child.getNodeType() != Node.TEXT_NODE) {
                            jTextArea4.append(child.getTextContent());
                            jTextArea4.append("\n");
                            jTextArea4.setCaretPosition(0);
                        }
                    }
                }
            }
        }

        // Mac
        if (os.contains("Mac")) {
            // Set label and textarea to nothing
            if (name.contains("empty")) {
                ImageIcon imagenone = new ImageIcon();
                jLabel2.setIcon(imagenone);
                jTextArea4.setText("");
            } else {
                // Load label and textarea with content of xml file and jpg
                ImageIcon imagenone = new ImageIcon();
                jLabel2.setIcon(imagenone);
                jTextArea4.setText("");

                ImageIcon imagemc = new ImageIcon(home + "/.minecrunch/resources/" + name + ".jpg");
                jLabel2.setIcon(imagemc);

                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = Factory.newDocumentBuilder();
                Document doc = builder.parse(home + "/.minecrunch/resources/" + name + "_client.xml");

                // creating an XPathFactory:
                XPathFactory factory = XPathFactory.newInstance();
                // using this factory to create an XPath object: 
                XPath xpath = factory.newXPath();

                // XPath Query for showing all nodes value
                XPathExpression expr = xpath.compile("client");
                Object result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element el = (Element) nodes.item(i);
                    NodeList children = el.getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node child = children.item(k);
                        if (child.getNodeType() != Node.TEXT_NODE) {
                            jTextArea4.append(child.getTextContent());
                            jTextArea4.append("\n");
                            jTextArea4.setCaretPosition(0);
                        }
                    }
                }
            }
        }
    }

    public void GetServerNode() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        // Get selected modpack name from jComboBox1
        selected = jComboBox1.getSelectedItem().toString();

        // Windows
        if (os.contains("Windows")) {
            // Read modpacks.xml and compare child node content to string provided by jCombox2
            DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = Factory.newDocumentBuilder();
            Document doc = builder.parse(home + "\\.minecrunch\\resources\\modpacks.xml");

            NodeList nodes = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if ((node.getNodeType() == Node.ELEMENT_NODE)) {
                    Element childElement = (Element) node;
                    if (childElement.getTextContent().contentEquals(selected)) {
                        name = childElement.getNodeName();
                    }

                }
            }
            LoadServerDetails();
        }

        // Linux
        if (os.contains("Linux")) {
            // Read modpacks.xml and compare child node content to string provided by jCombox2
            DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = Factory.newDocumentBuilder();
            Document doc = builder.parse(home + "/.minecrunch/resources/modpacks.xml");

            NodeList nodes = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if ((node.getNodeType() == Node.ELEMENT_NODE)) {
                    Element childElement = (Element) node;
                    if (childElement.getTextContent().contentEquals(selected)) {
                        name = childElement.getNodeName();
                    }

                }
            }
            LoadServerDetails();
        }

        // Mac
        if (os.contains("Mac")) {
            // Read modpacks.xml and compare child node content to string provided by jCombox2
            DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = Factory.newDocumentBuilder();
            Document doc = builder.parse(home + "/.minecrunch/resources/modpacks.xml");

            NodeList nodes = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if ((node.getNodeType() == Node.ELEMENT_NODE)) {
                    Element childElement = (Element) node;
                    if (childElement.getTextContent().contentEquals(selected)) {
                        name = childElement.getNodeName();
                    }

                }
            }
            LoadServerDetails();
        }
    }

    public void LoadServerDetails() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        // With name variable provided read specified xml and get specified jpg files then load those parameters into jLabel2 and jTextArea4
        // Windows
        if (os.contains("Windows")) {
            // Set label and textarea to nothing
            if (name.contains("empty")) {
                ImageIcon imagenone = new ImageIcon();
                jLabel1.setIcon(imagenone);
                jTextArea1.setText("");
            } else {
                // Load label and textarea with content of xml file and jpg
                ImageIcon imagenone = new ImageIcon();
                jLabel1.setIcon(imagenone);
                jTextArea1.setText("");
                ImageIcon imagemc = new ImageIcon(home + "\\.minecrunch\\resources\\" + name + ".jpg");
                jLabel1.setIcon(imagemc);

                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = Factory.newDocumentBuilder();
                Document doc = builder.parse(home + "\\.minecrunch\\resources\\" + name + "_server.xml");

                // creating an XPathFactory:
                XPathFactory factory = XPathFactory.newInstance();
                // using this factory to create an XPath object: 
                XPath xpath = factory.newXPath();

                // XPath Query for showing all nodes value
                XPathExpression expr = xpath.compile("client");
                Object result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element el = (Element) nodes.item(i);
                    NodeList children = el.getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node child = children.item(k);
                        if (child.getNodeType() != Node.TEXT_NODE) {
                            jTextArea1.append(child.getTextContent());
                            jTextArea1.append("\n");
                            jTextArea1.setCaretPosition(0);
                        }
                    }
                }
            }
        }

        // Linux
        if (os.contains("Linux")) {
            // Set label and textarea to nothing
            if (name.contains("empty")) {
                ImageIcon imagenone = new ImageIcon();
                jLabel1.setIcon(imagenone);
                jTextArea1.setText("");
            } else {
                // Load label and textarea with content of xml file and jpg
                ImageIcon imagenone = new ImageIcon();
                jLabel1.setIcon(imagenone);
                jTextArea1.setText("");

                ImageIcon imagemc = new ImageIcon(home + "/.minecrunch/resources/" + name + ".jpg");
                jLabel1.setIcon(imagemc);

                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = Factory.newDocumentBuilder();
                Document doc = builder.parse(home + "/.minecrunch/resources/" + name + "_server.xml");

                // creating an XPathFactory:
                XPathFactory factory = XPathFactory.newInstance();
                // using this factory to create an XPath object: 
                XPath xpath = factory.newXPath();

                // XPath Query for showing all nodes value
                XPathExpression expr = xpath.compile("client");
                Object result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element el = (Element) nodes.item(i);
                    NodeList children = el.getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node child = children.item(k);
                        if (child.getNodeType() != Node.TEXT_NODE) {
                            jTextArea1.append(child.getTextContent());
                            jTextArea1.append("\n");
                            jTextArea1.setCaretPosition(0);
                        }
                    }
                }
            }
        }

        // Mac
        if (os.contains("Mac")) {
            // Set label and textarea to nothing
            if (name.contains("empty")) {
                ImageIcon imagenone = new ImageIcon();
                jLabel1.setIcon(imagenone);
                jTextArea1.setText("");
            } else {
                // Load label and textarea with content of xml file and jpg
                ImageIcon imagenone = new ImageIcon();
                jLabel1.setIcon(imagenone);
                jTextArea1.setText("");

                ImageIcon imagemc = new ImageIcon(home + "/.minecrunch/resources/" + name + ".jpg");
                jLabel1.setIcon(imagemc);

                DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = Factory.newDocumentBuilder();
                Document doc = builder.parse(home + "/.minecrunch/resources/" + name + "_server.xml");

                // creating an XPathFactory:
                XPathFactory factory = XPathFactory.newInstance();
                // using this factory to create an XPath object: 
                XPath xpath = factory.newXPath();

                // XPath Query for showing all nodes value
                XPathExpression expr = xpath.compile("client");
                Object result = expr.evaluate(doc, XPathConstants.NODESET);
                NodeList nodes = (NodeList) result;
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element el = (Element) nodes.item(i);
                    NodeList children = el.getChildNodes();
                    for (int k = 0; k < children.getLength(); k++) {
                        Node child = children.item(k);
                        if (child.getNodeType() != Node.TEXT_NODE) {
                            jTextArea1.append(child.getTextContent());
                            jTextArea1.append("\n");
                            jTextArea1.setCaretPosition(0);
                        }
                    }
                }
            }
        }
    }

    public void InstallServer() {
        Object n = name;
        Server se = new Server(n);
        Thread server = new Thread(se);
        server.start();
    }

    public void InstallClient() {
            Object n = name;
            Object m = jComboBox2.getSelectedItem().toString();
            Client cl = new Client(n, m);
            Thread client = new Thread(cl);
            client.start();
    }

    public void LaunchMinecraft() {

        // Windows
        if (os.contains("Windows")) {
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "\\AppData\\Roaming\\.minecraft\\launcher.jar");
                System.exit(0);

            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_launcher.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Linux
        if (os.contains("Linux")) {
            try {
                Process proc = Runtime.getRuntime().exec("java -jar " + home + "/.minecraft/launcher.jar");
                System.exit(0);

            } catch (IOException ex) {
                Logger.getLogger(Minecrunch_launcher.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Mac
        try {
            Process proc = Runtime.getRuntime().exec("java -jar " + home + "/Library/Application Support/minecraft/launcher.jar");
            System.exit(0);

        } catch (IOException ex) {
            Logger.getLogger(Minecrunch_launcher.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() throws IOException {

        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane("http://www.minecrunch.net/download/news.html");
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minecrunch Modpacks");

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.RIGHT);

        jEditorPane1.setBorder(null);
        jScrollPane3.setViewportView(jEditorPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Breaking News", jPanel1);

        jScrollPane1.setBorder(null);

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(240, 240, 240));
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder("Server Details"));
        jScrollPane1.setViewportView(jTextArea1);

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel9.setText("Install Server:");

        jButton7.setText("Install Server");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, 270, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Servers", jPanel4);

        jLabel10.setText("Install Modpack:");

        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jButton8.setText("Install Modpack");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jScrollPane4.setBorder(null);

        jTextArea4.setEditable(false);
        jTextArea4.setBackground(new java.awt.Color(240, 240, 240));
        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jTextArea4.setWrapStyleWord(true);
        jTextArea4.setBorder(javax.swing.BorderFactory.createTitledBorder("Modpack Details"));
        jScrollPane4.setViewportView(jTextArea4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, 0, 256, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Modpacks", jPanel5);

        jButton2.setText("Launch Minecraft");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Exit();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Launch minecraft
        LaunchMinecraft();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // Install client button
        InstallClient();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Install server button
        InstallServer();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        try {
            GetServerNode();
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(PackUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        try {
            GetClientNode();
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(PackUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea4;
    // End of variables declaration//GEN-END:variables
}
