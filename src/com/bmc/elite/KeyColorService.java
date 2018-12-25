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

    Status lastStatus = null;

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

    public void setColorsFromBindings(Status status) {
        LedTools.setAllKeysFromColorArray(ColorGroups.OTHER);

        for(Map.Entry<Integer, Integer[]> pipPresetEntry : PipPresets.PIP_PRESET_COLORS.entrySet()) {
            LedTools.setKeyFromColorArray(pipPresetEntry.getKey(), pipPresetEntry.getValue());
        }

        if(ControlGroups.UI_MODE_CONTROLS.containsKey(status.GuiFocus)) {
            currentControlGroup = ControlGroups.UI_MODE_CONTROLS.get(status.GuiFocus);
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

        try {
            // We need to give time for color scheme to set
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatus() {
        try {
            jsonReader = new JsonReader(new FileReader(STATUS_FILE_PATH));

            status = gson.fromJson(jsonReader, Status.class);
            if(status == null) {
                if(Application.DEBUG) System.out.println("Cannot get status!");
                return;
            }
            setColorsFromBindings(status);
            setKeyColorFromStatus(status);
            lastStatus = status;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setKeyColorFromStatus(Status status) {
        try {
            if(currentControlGroup.containsKey(Controls.PlayerHUDModeToggle)) {
                if(isBitSet(status.Flags, Flags.HUD_DISCOVERY_MODE)) {
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

            String controlName;
            for(Map.Entry<String, StatusState> condition : pulsatingKeys.entrySet()) {
                controlName = condition.getKey();
                if(
                    currentControlGroup.containsKey(controlName)
                    && condition.getValue().conditionSatisfied(status.Flags, status.GuiFocus)
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

            for (Map.Entry<Integer, Integer[]> pipPreset : PipPresets.STATUS_TO_CONTROL.entrySet()) {
                if(Arrays.equals(pipPreset.getValue(), status.Pips)) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
