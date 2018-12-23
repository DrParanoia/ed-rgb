package com.bmc.elite;

import com.logitech.gaming.LogiLED;

public class Main {

    public static void main(String[] args) {
        try {
            LogiLED.LogiLedInit();
            KeyColorService keyColorService = new KeyColorService();

            FileWatcher.watchFile(KeyColorService.STATUS_FILE_PATH, keyColorService::setKeyColorFromStatus);
            FileWatcher.watchFile(BindingParser.getBindingsFile().getAbsolutePath(), keyColorService::initColors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
