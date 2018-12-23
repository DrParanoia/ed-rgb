package com.bmc.elite.config;

import com.bmc.elite.mappings.ColorGroups;
import com.logitech.gaming.LogiLED;

import java.util.HashMap;

public class PipPresets {
    public static final Integer[] G_1 = new Integer[] {4, 8, 0};
    public static final Integer[] G_2 = new Integer[] {8, 4, 0};
    public static final Integer[] G_3 = new Integer[] {4, 0, 8};
    public static final Integer[] G_4 = new Integer[] {1, 4, 7};
    public static final Integer[] G_5 = new Integer[] {0, 8, 4};

    public static final HashMap<Integer, Integer[]> STATUS_TO_CONTROL = new HashMap<Integer, Integer[]>() {
        {
            put(LogiLED.G_1, PipPresets.G_1);
            put(LogiLED.G_2, PipPresets.G_2);
            put(LogiLED.G_3, PipPresets.G_3);
            put(LogiLED.G_4, PipPresets.G_4);
            put(LogiLED.G_5, PipPresets.G_5);
        }
    };

    public static final HashMap<Integer, Integer[]> PIP_PRESET_COLORS = new HashMap<Integer, Integer[]>() {
        {
            put(LogiLED.G_1, ColorGroups.MOVEMENT_SPEED);
            put(LogiLED.G_2, ColorGroups.DEFENCE);
            put(LogiLED.G_3, ColorGroups.OFFENCE);
            put(LogiLED.G_4, ColorGroups.OFFENCE);
            put(LogiLED.G_5, ColorGroups.OFFENCE);
        }
    };
}
