package com.bmc.elite;

import com.bmc.elite.mappings.EliteKeysToLogitech;
import com.logitech.gaming.LogiLED;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LedTools {
    public static void setAllKeysFromColorArray(Integer[] colorArray) {
        if(colorArray != null) {
            LogiLED.LogiLedSetLighting(colorArray[0], colorArray[1], colorArray[2]);
        }
    }

    @Nullable
    public static Integer setKeyFromColorArray(int key, Integer[] colorArray) {
        Integer logitechKey = null;
        if(colorArray != null) {
            LogiLED.LogiLedSetLightingForKeyWithKeyName(key, colorArray[0], colorArray[1], colorArray[2]);
            logitechKey = key;
        }

        return logitechKey;
    }
    @Nullable
    public static Integer setEliteKeyFromColorArray(String eliteKeyName, Integer[] colorArray) {
        Integer logitechKey = null;
        if(EliteKeysToLogitech.KEY_MAP.containsKey(eliteKeyName)) {
            logitechKey = setKeyFromColorArray(EliteKeysToLogitech.KEY_MAP.get(eliteKeyName), colorArray);
        }

        return logitechKey;
    }
    public static List<Integer> setEliteKeysFromColorArray(List<String> eliteKeyNames, Integer[] colorArray) {
        List<Integer> logitechKeys = new ArrayList<>();
        if(eliteKeyNames != null) {
            for(String key : eliteKeyNames) {
                Integer logitechKey = setEliteKeyFromColorArray(key,colorArray);
                if(logitechKey != null) {
                    logitechKeys.add(logitechKey);
                }
            }
        }

        return logitechKeys;
    }
    public static void setKeyPulseFromColorArrays(int key, Integer[] colorArray1, Integer[] colorArray2, int msDuration, boolean isInfinite) {
        if(colorArray1 != null && colorArray2 != null) {
            LogiLED.LogiLedPulseSingleKey(
                key,
                colorArray1[0], colorArray1[1], colorArray1[2],
                colorArray2[0], colorArray2[1], colorArray2[2],
                msDuration, isInfinite
            );
        }
    }
    public static void setKeysPulseFromColorArrays(int[] keys, Integer[] colorArray1, Integer[] colorArray2, int msDuration, boolean isInfinite) {
        for(int key : keys) {
            setKeyPulseFromColorArrays(key, colorArray1, colorArray2, msDuration, isInfinite);
        }
    }
    public static void setEliteKeyPulseFromColorArrays(String eliteKeyName, Integer[] colorArray1, Integer[] colorArray2, int msDuration, boolean isInfinite) {
        if(EliteKeysToLogitech.KEY_MAP.containsKey(eliteKeyName)) {
            setKeyPulseFromColorArrays(EliteKeysToLogitech.KEY_MAP.get(eliteKeyName), colorArray1, colorArray2, msDuration, isInfinite);
        }
    }
    public static void setEliteKeysPulseFromColorArrays(List<String> eliteKeyNames, Integer[] colorArray1, Integer[] colorArray2, int msDuration, boolean isInfinite) {
        if(eliteKeyNames != null) {
            for(String key : eliteKeyNames) {
                setEliteKeyPulseFromColorArrays(key, colorArray1, colorArray2, msDuration, isInfinite);
            }
        }
    }
    public static void stopKeyEffects(int key) {
        LogiLED.LogiLedStopEffectsOnKey(key);
    }
    public static void stopEliteKeyEffect(String eliteKeyName) {
        if(EliteKeysToLogitech.KEY_MAP.containsKey(eliteKeyName)) {
            stopKeyEffects(EliteKeysToLogitech.KEY_MAP.get(eliteKeyName));
        }
    }
    public static void stopEliteKeysEffect(List<String> eliteKeyNames) {
        if(eliteKeyNames != null) {
            for(String key : eliteKeyNames) {
                stopEliteKeyEffect(key);
            }
        }
    }
    public static void saveKeyLighting(int key) {
        LogiLED.LogiLedSaveLightingForKey(key);
    }
    public static void saveEliteKeyLighting(String eliteKeyName) {
        if(EliteKeysToLogitech.KEY_MAP.containsKey(eliteKeyName)) {
            saveKeyLighting(EliteKeysToLogitech.KEY_MAP.get(eliteKeyName));
        }
    }
    public static void saveEliteKeysLighting(List<String> eliteKeyNames) {
        if(eliteKeyNames != null) {
            for(String key : eliteKeyNames) {
                saveEliteKeyLighting(key);
            }
        }
    }
    public static void restoreKeyLighting(int key) {
        LogiLED.LogiLedRestoreLightingForKey(key);
    }
    public static void restoreEliteKeyLighting(String eliteKeyName) {
        if(EliteKeysToLogitech.KEY_MAP.containsKey(eliteKeyName)) {
            restoreKeyLighting(EliteKeysToLogitech.KEY_MAP.get(eliteKeyName));
        }
    }
    public static void restoreEliteKeysLighting(List<String> eliteKeyNames) {
        if(eliteKeyNames != null) {
            for(String key : eliteKeyNames) {
                restoreEliteKeyLighting(key);
            }
        }
    }
}
