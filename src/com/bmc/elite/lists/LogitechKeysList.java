package com.bmc.elite.lists;

import com.bmc.elite.config.LedKeys;

import java.util.ArrayList;
import java.util.Arrays;

public class LogitechKeysList extends ArrayList<Integer> {
    {
        addAll(Arrays.asList(
            LedKeys.G_1,
            LedKeys.G_2,
            LedKeys.G_3,
            LedKeys.G_4,
            LedKeys.G_5,
            LedKeys.G_6,
            LedKeys.G_7,
            LedKeys.G_8,
            LedKeys.G_9,
            LedKeys.G_LOGO,
            LedKeys.G_BADGE
        ));
    }
}
