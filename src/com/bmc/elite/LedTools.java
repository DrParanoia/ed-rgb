package com.bmc.elite;

import com.bmc.elite.animations.AnimationHelper;
import com.bmc.elite.mappings.EliteKeysToLogitech;
import com.logitech.gaming.LogiLED;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LedTools {
    static AnimationHelper animationHelper = AnimationHelper.getInstance();

    public static void setAllKeysFromColorArray(Integer[] colorArray) {
        if(colorArray != null) {
            LogiLED.LogiLedSetLighting(colorArray[0], colorArray[1], colorArray[2]);
        }
    }

    @Nullable
    public static Integer setKeyFromColorArray(int key, Integer[] colorArray) {
        Integer logitechKey = null;
        if(colorArray != null) {
            animationHelper.stopKeyAnimation(key);
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
    public static Integer setKeyPulseFromColorArrays(int key, Integer[] colorArray1, Integer[] colorArray2, int msDuration, boolean isInfinite) {
        if(colorArray1 != null && colorArray2 != null) {
            animationHelper.pulseKey(key, colorArray1, colorArray2, msDuration, isInfinite);
            return key;
        }

        return null;
    }
    public static List<Integer> setKeysPulseFromColorArrays(int[] keys, Integer[] colorArray1, Integer[] colorArray2, int msDuration, boolean isInfinite) {
        List<Integer> logitechKeys = new ArrayList<>();
        for(int key : keys) {
            Integer logitechKey = setKeyPulseFromColorArrays(key, colorArray1, colorArray2, msDuration, isInfinite);
            if(logitechKey != null) {
                logitechKeys.add(logitechKey);
            }
        }

        return logitechKeys;
    }
    public static Integer setEliteKeyPulseFromColorArrays(String eliteKeyName, Integer[] colorArray1, Integer[] colorArray2, int msDuration, boolean isInfinite) {
        if(EliteKeysToLogitech.KEY_MAP.containsKey(eliteKeyName)) {
            return setKeyPulseFromColorArrays(EliteKeysToLogitech.KEY_MAP.get(eliteKeyName), colorArray1, colorArray2, msDuration, isInfinite);
        }

        return null;
    }
    public static List<Integer> setEliteKeysPulseFromColorArrays(List<String> eliteKeyNames, Integer[] colorArray1, Integer[] colorArray2, int msDuration, boolean isInfinite) {
        List<Integer> logitechKeys = new ArrayList<>();
        if(eliteKeyNames != null) {
            for(String key : eliteKeyNames) {
                Integer logitechKey = setEliteKeyPulseFromColorArrays(key, colorArray1, colorArray2, msDuration, isInfinite);
                if(logitechKey != null) {
                    logitechKeys.add(logitechKey);
                }
            }
        }

        return logitechKeys;
    }
    public static void stopKeyEffects(int key) {
        animationHelper.stopKeyAnimation(key);
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
