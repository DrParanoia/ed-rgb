package com.bmc.elite;

import com.logitech.gaming.LogiLED;

public class EliteLed {

    boolean enabled = false;
    FileWatcher statusFileWatcher;
    FileWatcher bindingsFileWatcher;

    public void enable() {
        if(!enabled) {
            enabled = true;
            LogiLED.LogiLedInit();
            KeyColorService keyColorService = new KeyColorService();

            statusFileWatcher = new FileWatcher(
                KeyColorService.STATUS_FILE_PATH,
                keyColorService::setKeyColorFromStatus
            );
            bindingsFileWatcher = new FileWatcher(
                BindingParser.getBindingsFile().getAbsolutePath(),
                () -> keyColorService.initColors(true)
            );
        }
    }
    public void disable() {
        if(enabled) {
            if(statusFileWatcher != null) {
                statusFileWatcher.stop();
                statusFileWatcher = null;
            }
            if(bindingsFileWatcher != null) {
                bindingsFileWatcher.stop();
                bindingsFileWatcher = null;
            }
            LogiLED.LogiLedShutdown();
            enabled = false;
        }
    }
}
