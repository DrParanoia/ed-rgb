package com.bmc.elite;

import com.bmc.elite.models.EDStatus;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.logitech.gaming.LogiLED;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class EDKeyService {

    Gson gson;
    JsonReader jsonReader;
    EDStatus edStatus;

    public static int PULSE_DURATION = 200;
    public static int PIP_PRESET_PULSE_DURATION = 500;
    public static String FRONTIER_DIRECTORY_PATH = System.getProperty("user.home")
        + File.separator + "Saved Games"
        + File.separator + "Frontier Developments"
        + File.separator + "Elite Dangerous";
    public static final String STATUS_FILE_NAME = "Status.json";
    public static String STATUS_FILE_PATH = FRONTIER_DIRECTORY_PATH + File.separator + STATUS_FILE_NAME;

    public static boolean isBitSet(long bitmask, int flag) {
        return (bitmask & flag) == flag;
    }

    public static HashMap<String, List<String>> eliteBindings = EDControlsHelper.getBindings();
    public static HashMap<String, Integer[]> controlColorMap = EDControlGroups.getControlToColorMap();

    public static final int[] PULSING_TOGGLES = new int[] {
        EDStatusFlags.LANDING_GEAR, EDStatusFlags.HARDPOINTS, EDStatusFlags.SHIP_LIGHTS, EDStatusFlags.CARGO_SCOOP
    };

    public EDKeyService() {
        gson = new Gson();
        setColorsFromBindings();
        setKeyColorFromStatus();
    }

    public void setColorsFromBindings() {
        KeyTools.setAllKeysFromColorArray(EDColors.OTHER);

        for(Map.Entry<Integer, Integer[]> pipPresetEntry : EDPipPresets.PIP_PRESET_COLORS.entrySet()) {
            KeyTools.setKeyFromColorArray(pipPresetEntry.getKey(), pipPresetEntry.getValue());
        }

        List<String> keyBinds;
        String controlName;
        for (Map.Entry<String, List<String>> control : eliteBindings.entrySet()) {
            controlName = control.getKey();
            keyBinds = control.getValue();
            for (String bind : keyBinds) {
                KeyTools.setEliteKeyFromColorArray(bind, controlColorMap.get(controlName));
            }
        }
    }

    public void setKeyColorFromStatus() {
        try {
            jsonReader = new JsonReader(new FileReader(STATUS_FILE_PATH));

            edStatus = gson.fromJson(jsonReader, EDStatus.class);
            if(edStatus == null) {
                System.out.println("Cannot get status!");
                return;
            }

            if(isBitSet(edStatus.Flags, EDStatusFlags.HUD_DISCOVERY_MODE)) {
                KeyTools.setKeyFromColorArray(LogiLED.PERIOD, EDColors.HUD_MODE_DISCOVERY);
            } else {
                KeyTools.setKeyFromColorArray(LogiLED.PERIOD, EDColors.HUD_MODE_COMBAT);
            }

            String controlName;
            for(int pulsingStatusFlag : PULSING_TOGGLES) {
                controlName = EDStatusFlags.STATUS_TO_CONTROL.get(pulsingStatusFlag);
                if(isBitSet(edStatus.Flags, pulsingStatusFlag)) {
                    KeyTools.setEliteKeysFromColorArray(
                        eliteBindings.get(controlName),
                        controlColorMap.get(controlName)
                    );
                    KeyTools.setEliteKeysPulseFromColorArrays(
                        eliteBindings.get(controlName),
                        EDColors.OTHER,
                        controlColorMap.get(controlName),
                        PULSE_DURATION,
                        true
                    );
                } else {
                    KeyTools.stopEliteKeysEffect(eliteBindings.get(controlName));
                }
            }

            for (Map.Entry<Integer, Integer[]> pipPreset : EDPipPresets.STATUS_TO_CONTROL.entrySet()) {
                if(Arrays.equals(pipPreset.getValue(), edStatus.Pips)) {
                    KeyTools.setKeyFromColorArray(
                        pipPreset.getKey(),
                        EDPipPresets.PIP_PRESET_COLORS.get(pipPreset.getKey())
                    );
                    KeyTools.setKeyPulseFromColorArrays(
                        pipPreset.getKey(),
                        EDColors.OTHER,
                        EDPipPresets.PIP_PRESET_COLORS.get(pipPreset.getKey()),
                        PIP_PRESET_PULSE_DURATION,
                        true
                    );
                } else {
                    KeyTools.stopKeyEffects(pipPreset.getKey());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
