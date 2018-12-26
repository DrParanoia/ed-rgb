package com.bmc.elite.config;

import java.io.File;

public class Application {
    public static final boolean DEBUG = true;
    public static int PULSE_DURATION = 200;
    public static int DELAY_AFTER_COLOR_SET = 250;
    public static String FRONTIER_DIRECTORY_PATH = System.getProperty("user.home")
        + File.separator + "Saved Games"
        + File.separator + "Frontier Developments"
        + File.separator + "Elite Dangerous";

    public static final String STATUS_FILE_NAME = "Status.json";
    public static String STATUS_FILE_PATH = FRONTIER_DIRECTORY_PATH + File.separator + STATUS_FILE_NAME;
}
