package com.bmc.elite;

import com.bmc.elite.config.PipPresets;
import com.bmc.elite.config.PulsatingKeys;
import com.bmc.elite.mappings.ColorGroups;
import com.bmc.elite.mappings.ControlGroups;
import com.bmc.elite.mappings.Controls;
import com.bmc.elite.models.Status;
import com.bmc.elite.status.Flags;
import com.bmc.elite.status.GuiFocus;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class KeyColorService {

    Gson gson;
    JsonReader jsonReader;

    public static int PULSE_DURATION = 200;
    public static int DELEY_AFTER_COLOR_SET = 250;
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

    public static HashMap<String, Integer[]> MAIN_CONTROLS = ControlGroups.getControlToColorMap(ControlGroups.MAIN_CONTROLS);

    HashMap<String, Integer[]> currentControlGroup;

    public PulsatingKeys pulsatingKeys = new PulsatingKeys();

    public KeyColorService() {
        gson = new Gson();
        initColors();
    }

    public void initColors() {
        initColors(false);
    }
    public void initColors(boolean resetBindings) {
        eliteBindings = BindingParser.getBindings(resetBindings);
        updateStatus();
    }

    public void setColorsFromBindings(Status newStatus) {
        LedTools.setAllKeysFromColorArray(ColorGroups.OTHER);

        if(ControlGroups.UI_MODE_CONTROLS.containsKey(newStatus.GuiFocus)) {
            currentControlGroup = ControlGroups.UI_MODE_CONTROLS.get(newStatus.GuiFocus);
        } else {
            currentControlGroup = ControlGroups.UI_MODE_CONTROLS.get(GuiFocus.NONE);
        }

        List<String> keys;
        for(Map.Entry<String, Integer[]> controlColors : currentControlGroup.entrySet()) {
            keys = eliteBindings.get(controlColors.getKey());
            if(keys != null) {
                LedTools.setEliteKeysFromColorArray(keys, controlColors.getValue());
            }
        }

        setToggleKeyColors(newStatus);

        try {
            // We need to give time for color scheme to set
            Thread.sleep(DELEY_AFTER_COLOR_SET);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatus() {
        try {
            jsonReader = new JsonReader(new FileReader(STATUS_FILE_PATH));

            Status newStatus = gson.fromJson(jsonReader, Status.class);
            if(newStatus == null) {
                if(Application.DEBUG) System.out.println("Cannot get status!");
                return;
            }
            setColorsFromBindings(newStatus);
            setKeyColorFromStatus(newStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setToggleKeyColors(Status newStatus) {
        // Custom PIP presets for G keys
        for (Map.Entry<Integer, Integer[]> pipPreset : PipPresets.STATUS_TO_CONTROL.entrySet()) {
            if(Arrays.equals(pipPreset.getValue(), newStatus.Pips)) {
                LedTools.setKeyFromColorArray(
                    pipPreset.getKey(),
                    PipPresets.PIP_PRESET_COLORS.get(pipPreset.getKey())
                );
            } else {
                LedTools.setKeyFromColorArray(
                    pipPreset.getKey(),
                    PipPresets.PIP_PRESET_COLORS_DISABLED.get(pipPreset.getKey())
                );
            }
        }

        // Change HUD Mode key color based on current HUD mode (Discovery/Combat)
        if(currentControlGroup.containsKey(Controls.PlayerHUDModeToggle)) {
            if(isBitSet(newStatus.Flags, Flags.HUD_DISCOVERY_MODE)) {
                LedTools.setEliteKeysFromColorArray(
                    eliteBindings.get(Controls.PlayerHUDModeToggle),
                    ColorGroups.HUD_MODE_DISCOVERY
                );
            } else {
                LedTools.setEliteKeysFromColorArray(
                    eliteBindings.get(Controls.PlayerHUDModeToggle),
                    ColorGroups.HUD_MODE_COMBAT
                );
            }
        }
    }

    public void setKeyColorFromStatus(Status newStatus) {
        try {
            String controlName;
            for(Map.Entry<String, StatusState> condition : pulsatingKeys.entrySet()) {
                controlName = condition.getKey();
                if(
                    currentControlGroup.containsKey(controlName)
                    && condition.getValue().conditionSatisfied(newStatus.Flags, newStatus.GuiFocus)
                ) {
                    LedTools.setEliteKeysPulseFromColorArrays(
                        eliteBindings.get(controlName),
                        ColorGroups.OTHER,
                        MAIN_CONTROLS.get(controlName),
                        PULSE_DURATION,
                        true
                    );
                } else {
                    LedTools.stopEliteKeysEffect(eliteBindings.get(controlName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
