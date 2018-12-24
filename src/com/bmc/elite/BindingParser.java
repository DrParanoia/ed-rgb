package com.bmc.elite;

import com.bmc.elite.mappings.ControlGroups;
import com.bmc.elite.mappings.ControlNames;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BindingParser {
    public static String FRONTIER_BINDINGS_PATH = System.getProperty("user.home")
            + File.separator + "AppData"
            + File.separator + "Local"
            + File.separator + "Frontier Developments"
            + File.separator + "Elite Dangerous"
            + File.separator + "Options"
            + File.separator + "Bindings";

    public static String PRESET_FILE = FRONTIER_BINDINGS_PATH + File.separator + "StartPreset.start";

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                result = sb.toString();
            } finally {
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static File getBindingsFile() {
        String presetName = readFile(PRESET_FILE).trim();

        File bindingsFolder = new File(FRONTIER_BINDINGS_PATH);
        String currentFileName;
        if(Application.DEBUG) System.out.println("Searching for preset: " + presetName);
        for (final File fileEntry : bindingsFolder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                currentFileName = fileEntry.getName();
                if(currentFileName.endsWith(".binds")) {
                    if(currentFileName.startsWith(presetName + ".")) {
                        if(Application.DEBUG) System.out.println("Found bindings: " + currentFileName);
                        return fileEntry;
                    }
                }
            }
        }

        return null;
    }

    public static HashMap<String, List<String>> getBindings() {
        File bindingsFile = getBindingsFile();
        if(bindingsFile != null) {
            return parseBindings(bindingsFile);
        }

        return null;
    }

    private static void processKeyBind(Node bind, List<String> keyList, List<String> modifierKeys) {
        NamedNodeMap bindAttributes, modifierAttributes;
        String keyName = "";
        if(bind != null) {
            bindAttributes = bind.getAttributes();
            if(bindAttributes.getNamedItem("Device").getTextContent().equals("Keyboard")) {
                keyName = bindAttributes.getNamedItem("Key").getTextContent();
            }
            if(bind.hasChildNodes()) {
                NodeList bindChildren = bind.getChildNodes();
                for(int i = 0, length = bindChildren.getLength(); i < length; i++) {
                    Node bindChild = bindChildren.item(i);
                    if(bindChild.getNodeName().equals("Modifier")) {
                        modifierAttributes = bindChild.getAttributes();
                        if(modifierAttributes.getNamedItem("Device").getTextContent().equals("Keyboard")) {
                            String modifierKey = modifierAttributes.getNamedItem("Key").getTextContent();
                            if(!modifierKey.equals("") && !modifierKeys.contains(modifierKey)) {
                                modifierKeys.add(modifierKey);
                            }
                        }
                    }
                }
            }
        }
        if(!keyName.equals("")) {
            keyList.add(keyName);
        }
    }
    private static HashMap<String, List<String>> parseBindings(File bindingsFile) {
        HashMap<String, List<String>> result = new HashMap<>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(bindingsFile);
            Element root = document.getDocumentElement();

            NodeList bindings = root.getChildNodes();

            Node binding;
            String controlName;
            HashMap<String, Integer[]> controlColorMap = ControlGroups.getControlToColorMap();

            NodeList controlChildNodes;
            Node controlChild, primaryBind, secondaryBind;
            List<String> modifierKeyList = new ArrayList<>();
            for(int i = 0, length = bindings.getLength(); i < length; i++) {
                // Iterating through all binding elements
                binding = bindings.item(i);
                controlName = binding.getNodeName();

                primaryBind = null;
                secondaryBind = null;

                if(controlColorMap.containsKey(controlName)) {
                    controlChildNodes = binding.getChildNodes();
                    if(controlChildNodes.getLength() <= 0) continue;
                    for(int i2 = 0, length2 = controlChildNodes.getLength(); i2 < length2; i2++) {
                        controlChild = controlChildNodes.item(i2);
                        if(controlChild.getNodeName().equals("Primary")) {
                            primaryBind = controlChild;
                        } else if(controlChild.getNodeName().equals("Secondary")) {
                            secondaryBind = controlChild;
                        }
                    }

                    List<String> keyList = new ArrayList<>();
                    processKeyBind(primaryBind, keyList, modifierKeyList);
                    processKeyBind(secondaryBind, keyList, modifierKeyList);
                    if(!keyList.isEmpty()) {
                        result.put(controlName, keyList);
                    }
                }
            }

            if(!modifierKeyList.isEmpty()) {
                result.put(ControlNames.MODIFIER, modifierKeyList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
