package com.bmc.elite.mappings;

import com.logitech.gaming.LogiLED;

import java.util.HashMap;

public class LogitechAnimationKeys {
    public static final HashMap<Integer, HashMap<Integer, Integer>> ANIMATOR_KEYS = new HashMap<Integer, HashMap<Integer, Integer>>() {
        {
            put(10, new HashMap<Integer, Integer>() {
                {
                    put(1, LogiLED.G_LOGO);
                    put(2, LogiLED.G_BADGE);
                }
            });
            put(4, new HashMap<Integer, Integer>() {
                {
                    put(1, LogiLED.G_1);
                    put(2, LogiLED.G_2);
                    put(3, LogiLED.G_3);
                    put(4, LogiLED.G_4);
                    put(5, LogiLED.G_5);
                    put(6, LogiLED.G_6);
                    put(7, LogiLED.G_7);
                    put(8, LogiLED.G_8);
                    put(9, LogiLED.G_9);
                }
            });
        }
    };
}
