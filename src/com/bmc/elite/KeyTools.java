package com.bmc.elite;

import com.bmc.elite.animations.AnimationHelper;
import com.bmc.elite.mappings.KeyMaps;
import com.logitech.gaming.LogiLED;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyTools {
    static AnimationHelper animationHelper = AnimationHelper.getInstance();

    private static Map<Integer, Integer[]> currentKeyNameColors = new HashMap<>();
    private static Map<Integer, Integer[]> currentKeyHidColors = new HashMap<>();

    @Nullable
    public static Integer[] getCurrentKeyNameColor(int keyName) {
        return currentKeyNameColors.get(keyName);
    }
    @Nullable
    public static Integer[] getCurrentKeyHidColor(int hidCode) {
        return currentKeyHidColors.get(hidCode);
    }
    public static void clearColorCache() {
        currentKeyNameColors.clear();
        currentKeyHidColors.clear();
    }
    public static boolean isColorCacheEmpty() {
        return currentKeyNameColors.isEmpty() && currentKeyHidColors.isEmpty();
    }

    public static List<Integer> getHidsFromEliteKeys(List<String> eliteKeyNames) {
        List<Integer> hidKeys = new ArrayList<>();

        for(String eliteKeyName : eliteKeyNames) {
            if(KeyMaps.ELITE_HID.containsKey(eliteKeyName)) {
                hidKeys.add(KeyMaps.ELITE_HID.get(eliteKeyName));
            }
        }

        return hidKeys;
    }

    public static int setLightingForKeyWithKeyName(int keyName, int redPercentage, int greenPercentage, int bluePercentage) {
        LogiLED.LogiLedSetLightingForKeyWithKeyName(keyName, redPercentage, greenPercentage, bluePercentage);
        currentKeyNameColors.put(keyName, new Integer[] {redPercentage, greenPercentage, bluePercentage});
        return keyName;
    }
    public static int setLightingForKeyWithHidCode(int hidCode, int redPercentage, int greenPercentage, int bluePercentage) {
        LogiLED.LogiLedSetLightingForKeyWithHidCode(hidCode, redPercentage, greenPercentage, bluePercentage);
        currentKeyHidColors.put(hidCode, new Integer[] {redPercentage, greenPercentage, bluePercentage});
        return hidCode;
    }

    public static void setAllKeysFromColorArray(Integer[] colorArray) {
        if(colorArray != null) {
            LogiLED.LogiLedSetLighting(colorArray[0], colorArray[1], colorArray[2]);
        }
    }

    @Nullable
    public static Integer setKeyFromColorArray(int key, Integer[] colorArray) {
        return setKeyFromColorArray(key, colorArray, false);
    }

    @Nullable
    public static Integer setKeyFromColorArray(int key, Integer[] colorArray, boolean logitech) {
        Integer logitechKey = null;
        if(colorArray != null) {
            animationHelper.stopKeyAnimation(key);
            if(logitech) {
                setLightingForKeyWithKeyName(key, colorArray[0], colorArray[1], colorArray[2]);
            } else {
                setLightingForKeyWithHidCode(key, colorArray[0], colorArray[1], colorArray[2]);
            }
            logitechKey = key;
        }

        return logitechKey;
    }
    @Nullable
    public static Integer setEliteKeyFromColorArray(String eliteKeyName, Integer[] colorArray) {
        Integer logitechKey = null;
        if(KeyMaps.ELITE_HID.containsKey(eliteKeyName)) {
            logitechKey = setKeyFromColorArray(KeyMaps.ELITE_HID.get(eliteKeyName), colorArray);
        }

        return logitechKey;
    }
    public static List<Integer> setEliteKeysFromColorArray(List<String> eliteKeyNames, Integer[] colorArray) {
        List<Integer> logitechKeys = new ArrayList<>();
        if(eliteKeyNames != null) {
            for(String key : eliteKeyNames) {
                Integer logitechKey = setEliteKeyFromColorArray(key, colorArray);
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
        if(KeyMaps.ELITE_HID.containsKey(eliteKeyName)) {
            return setKeyPulseFromColorArrays(KeyMaps.ELITE_HID.get(eliteKeyName), colorArray1, colorArray2, msDuration, isInfinite);
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
        if(KeyMaps.ELITE_HID.containsKey(eliteKeyName)) {
            stopKeyEffects(KeyMaps.ELITE_HID.get(eliteKeyName));
        }
    }
    public static void stopEliteKeysEffect(List<String> eliteKeyNames) {
        if(eliteKeyNames != null) {
            for(String key : eliteKeyNames) {
                stopEliteKeyEffect(key);
            }
        }
    }
    public static Integer dimEliteKeyFromColorArray(String eliteKeyName, Integer[] colorArray, double percent) {
        Integer[] newColor = {(int)(colorArray[0]*percent), (int)(colorArray[1]*percent), (int)(colorArray[2]*percent)};
        /*colorArray[0] = colorArray[0]*(percent/100);
        colorArray[1] = colorArray[1]*(percent/100);
        colorArray[2] = colorArray[2]*(percent/100);*/
        return setEliteKeyFromColorArray(eliteKeyName, newColor);
    }
}
