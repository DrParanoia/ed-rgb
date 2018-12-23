package com.bmc.elite;

import com.bmc.elite.mappings.EliteKeysToLogitech;
import com.logitech.gaming.LogiLED;

import java.util.List;

public class LedTools {
    public static void setAllKeysFromColorArray(Integer[] colorArray) {
        if(colorArray != null) {
            LogiLED.LogiLedSetLighting(colorArray[0], colorArray[1], colorArray[2]);
        }
    }
    public static void setKeyFromColorArray(int key, Integer[] colorArray) {
        if(colorArray != null) {
            LogiLED.LogiLedSetLightingForKeyWithKeyName(key, colorArray[0], colorArray[1], colorArray[2]);
        }
    }
    public static void setEliteKeyFromColorArray(String eliteKeyName, Integer[] colorArray) {
        if(EliteKeysToLogitech.KEY_MAP.containsKey(eliteKeyName)) {
            setKeyFromColorArray(EliteKeysToLogitech.KEY_MAP.get(eliteKeyName), colorArray);
        }
    }
    public static void setEliteKeysFromColorArray(List<String> eliteKeyNames, Integer[] colorArray) {
        if(eliteKeyNames != null) {
            for(String key : eliteKeyNames) {
                setEliteKeyFromColorArray(key,colorArray);
            }
        }
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
}
