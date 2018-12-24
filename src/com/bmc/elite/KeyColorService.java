package com.bmc.elite;

import com.bmc.elite.config.PipPresets;
import com.bmc.elite.config.PulsatingKeys;
import com.bmc.elite.mappings.ColorGroups;
import com.bmc.elite.mappings.ControlGroups;
import com.bmc.elite.mappings.ControlNames;
import com.bmc.elite.models.Status;
import com.bmc.elite.values.StatusFlags;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.logitech.gaming.LogiLED;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class KeyColorService {

    Gson gson;
    JsonReader jsonReader;
    Status status;

    public static int PULSE_DURATION = 200;
    public static int PIP_PRESET_PULSE_DURATION = 2000;
    public static String FRONTIER_DIRECTORY_PATH = System.getProperty("user.home")
        + File.separator + "Saved Games"
        + File.separator + "Frontier Developments"
        + File.separator + "Elite Dangerous";
    public static final String STATUS_FILE_NAME = "Status.json";
    public static String STATUS_FILE_PATH = FRONTIER_DIRECTORY_PATH + File.separator + STATUS_FILE_NAME;

    public static boolean isBitSet(long bitmask, int flag) {
        return (bitmask & flag) == flag;
    }

    public static HashMap<String, List<String>> eliteBindings;
    public static HashMap<String, Integer[]> controlColorMap;

    public PulsatingKeys pulsatingKeys = new PulsatingKeys();

    public KeyColorService() {
        gson = new Gson();
        initColors();
    }

    public void initColors() {
        eliteBindings = BindingParser.getBindings();
        controlColorMap = ControlGroups.getControlToColorMap();
        setColorsFromBindings();
        setKeyColorFromStatus();
    }

    public void setColorsFromBindings() {
        LedTools.setAllKeysFromColorArray(ColorGroups.OTHER);

        for(Map.Entry<Integer, Integer[]> pipPresetEntry : PipPresets.PIP_PRESET_COLORS.entrySet()) {
            LedTools.setKeyFromColorArray(pipPresetEntry.getKey(), pipPresetEntry.getValue());
        }

        List<String> keyBinds;
        String controlName;
        for (Map.Entry<String, List<String>> control : eliteBindings.entrySet()) {
            controlName = control.getKey();
            keyBinds = control.getValue();
            for (String bind : keyBinds) {
                LedTools.setEliteKeyFromColorArray(bind, controlColorMap.get(controlName));
            }
        }
    }

    public void setKeyColorFromStatus() {
        try {
            jsonReader = new JsonReader(new FileReader(STATUS_FILE_PATH));

            status = gson.fromJson(jsonReader, Status.class);
            if(status == null) {
                System.out.println("Cannot get status!");
                return;
            }

            if(isBitSet(status.Flags, StatusFlags.HUD_DISCOVERY_MODE)) {
                LedTools.setEliteKeysFromColorArray(
                    eliteBindings.get(ControlNames.PlayerHUDModeToggle),
                    ColorGroups.HUD_MODE_DISCOVERY
                );
            } else {
                LedTools.setEliteKeysFromColorArray(
                    eliteBindings.get(ControlNames.PlayerHUDModeToggle),
                    ColorGroups.HUD_MODE_COMBAT
                );
            }

            String controlName;
            for(Map.Entry<String, StatusState> condition : pulsatingKeys.entrySet()) {
                controlName = condition.getKey();
                if(condition.getValue().conditionSatisfied(status.Flags, status.GuiFocus)) {
                    LedTools.setEliteKeysFromColorArray(
                        eliteBindings.get(controlName),
                        controlColorMap.get(controlName)
                    );
                    LedTools.setEliteKeysPulseFromColorArrays(
                        eliteBindings.get(controlName),
                        ColorGroups.OTHER,
                        controlColorMap.get(controlName),
                        PULSE_DURATION,
                        true
                    );
                } else {
                    LedTools.stopEliteKeysEffect(eliteBindings.get(controlName));
                }
            }

            for (Map.Entry<Integer, Integer[]> pipPreset : PipPresets.STATUS_TO_CONTROL.entrySet()) {
                if(Arrays.equals(pipPreset.getValue(), status.Pips)) {
                    LedTools.setKeyFromColorArray(
                        pipPreset.getKey(),
                        PipPresets.PIP_PRESET_COLORS.get(pipPreset.getKey())
                    );
//                    LedTools.setKeyPulseFromColorArrays(
//                        pipPreset.getKey(),
//                        ColorGroups.OTHER,
//                        PipPresets.PIP_PRESET_COLORS.get(pipPreset.getKey()),
//                        PIP_PRESET_PULSE_DURATION,
//                        true
//                    );
                } else {
//                    LedTools.stopKeyEffects(pipPreset.getKey());
                    LedTools.setKeyFromColorArray(
                            pipPreset.getKey(),
                            PipPresets.PIP_PRESET_COLORS_DISABLED.get(pipPreset.getKey())
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
