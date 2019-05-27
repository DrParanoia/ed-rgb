package com.bmc.elite;

import com.bmc.elite.config.Application;
import com.bmc.elite.gui.MainWindow;
import com.bmc.elite.mappings.Controls;
import com.bmc.elite.models.EliteBind;
import com.bmc.elite.models.EliteBindList;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
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
    public static String DEFAULT_BINDINGS_PATH = "src/defaultBinds";

    public static String PRESET_FILE = FRONTIER_BINDINGS_PATH + File.separator + "StartPreset.start";

    public static List<String> allModifiers = new ArrayList<>();
    public static List<List<String>> modifierCombinations = new ArrayList<>();
    public static List<EliteBind> bindsWithModifiers = new ArrayList<>();
    private static HashMap<String, EliteBindList> bindings = null;
    private static File bindingsFile = null;

    public static File getBindingsFile() {
        return getBindingsFile(false);
    }
    public static File getBindingsFile(boolean resetBindings) {
        if(!resetBindings && bindingsFile != null) {
            return bindingsFile;
        }
        bindingsFile = null;
        String presetName = FileUtils.readFile(PRESET_FILE).trim();

        File bindingsFolder = new File(FRONTIER_BINDINGS_PATH);
        if(Application.DEBUG) LogUtils.log("Searching for preset: " + presetName);
		for (final File fileEntry : bindingsFolder.listFiles()) {
			if (!fileEntry.isDirectory() && fileEntry.getName().startsWith(presetName) && fileEntry.getName().endsWith(".binds")) {
                if(Application.DEBUG) LogUtils.log("Found bindings: " + fileEntry.getName());
                bindingsFile =  fileEntry;
                break;
            }
		}

        if (bindingsFile == null) {
            if(Application.DEBUG) LogUtils.log("Could not find preset. Searching default presets.");
            bindingsFolder = new File(DEFAULT_BINDINGS_PATH);
            for (final File fileEntry : bindingsFolder.listFiles()) {
                if (!fileEntry.isDirectory() && fileEntry.getName().equals(presetName + ".binds")) {
                    if(Application.DEBUG) LogUtils.log("Found bindings: " + fileEntry.getName());
                    bindingsFile =  fileEntry;
                    break;
                }
            }
        }

        //TODO find way to handle default binds that are stored in the games install path, using the same as above works but not sure how to find the install path.
		if (bindingsFile == null) {
			LogUtils.log("Could not find preset: " + presetName);
			bindingsFile = MainWindow.MissingBinds();
		}

        return bindingsFile;
    }

	public static HashMap<String, EliteBindList> getBindings(boolean resetBindings) {
        if(!resetBindings && bindings != null) {
            return bindings;
        }
        bindings = null;

        File bindingsFile = getBindingsFile(resetBindings);
        if(bindingsFile != null) {
            bindings = parseBindings(bindingsFile);
        }

        return bindings;
    }

    @Nullable
    private static EliteBind processKeyBind(Node bind, List<EliteBind> modifierKeys) {
        EliteBind eliteBind = new EliteBind();

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

                            // TODO: Current modifier highlighting logic is a hack. Needs to be redone.
                            EliteBind modifierBind = new EliteBind(modifierKey);
                            if(!modifierKey.equals("") && !modifierKeys.contains(modifierBind)) {
                                eliteBind.addModifier(modifierKey);
                                modifierKeys.add(modifierBind);
                            }
                        }
                    }
                }
            }
        }
        if(!keyName.equals("")) {
            eliteBind.setKey(keyName);
            return eliteBind;
        }

        return null;
    }
    private static HashMap<String, EliteBindList> parseBindings(File bindingsFile) {
        HashMap<String, EliteBindList> result = new HashMap<>();
        try {
            allModifiers.clear();
            modifierCombinations.clear();
            bindsWithModifiers.clear();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(bindingsFile);
            Element root = document.getDocumentElement();

            NodeList bindings = root.getChildNodes();

            Node binding;
            String controlName;

            NodeList controlChildNodes;
            Node controlChild, primaryBind, secondaryBind;
            EliteBindList modifierBindList = new EliteBindList();
            for(int i = 0, length = bindings.getLength(); i < length; i++) {
                // Iterating through all binding elements
                binding = bindings.item(i);
                controlName = binding.getNodeName();

                primaryBind = null;
                secondaryBind = null;

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

                EliteBindList eliteBindList = new EliteBindList();
                EliteBind primaryEliteBind = processKeyBind(primaryBind, modifierBindList);
                EliteBind secondaryEliteBind = processKeyBind(secondaryBind, modifierBindList);

                if(primaryEliteBind != null) {
                    eliteBindList.add(primaryEliteBind);
                    if(primaryEliteBind.hasModifiers()) {
                        modifierCombinations.add(primaryEliteBind.getModifiers());
                        allModifiers.addAll(primaryEliteBind.getModifiers());
                        bindsWithModifiers.add(primaryEliteBind);
                    }
                }
                if(secondaryEliteBind != null) {
                    eliteBindList.add(secondaryEliteBind);
                    if(secondaryEliteBind.hasModifiers()) {
                        modifierCombinations.add(secondaryEliteBind.getModifiers());
                        allModifiers.addAll(secondaryEliteBind.getModifiers());
                        bindsWithModifiers.add(secondaryEliteBind);
                    }
                }
                if(!eliteBindList.isEmpty()) {
                    result.put(controlName, eliteBindList);
                }
            }

            if(!modifierBindList.isEmpty()) {
                result.put(Controls.MODIFIER, modifierBindList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
