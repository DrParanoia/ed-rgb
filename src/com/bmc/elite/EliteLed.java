package com.bmc.elite;

import com.bmc.elite.callbacks.JournalCallback;
import com.bmc.elite.config.Application;
import com.bmc.elite.models.JournalEvent;
import com.logitech.gaming.LogiLED;

import java.util.List;

public class EliteLed {

    boolean enabled = false;
    FileWatcher statusFileWatcher;
    FileWatcher bindingsFileWatcher;
    JournalWatcher journalWatcher;

    public void enable() {
        if(!enabled) {
            enabled = true;
            LogiLED.LogiLedInit();
            LogiLED.LogiLedSetTargetDevice(LogiLED.LOGI_DEVICETYPE_PERKEY_RGB);
            KeyColorService keyColorService = new KeyColorService();

            statusFileWatcher = new FileWatcher(
                Application.STATUS_FILE_PATH,
                changedFile -> keyColorService.updateStatus()
            );
            bindingsFileWatcher = new FileWatcher(
                BindingParser.getBindingsFile().getAbsolutePath(),
                changedFile -> keyColorService.initColors(true)
            );
            if(journalWatcher == null) {
                journalWatcher = new JournalWatcher(new JournalCallback() {
                    @Override
                    public void journalChanged(List<JournalEvent> newEvents) {
                        if(Application.DEBUG) LogUtils.log("Got new journal events");
                        for(JournalEvent journalEvent : newEvents) {
                            LogUtils.log(journalEvent.event);
                        }
                    }
                });
            }
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
//            if(journalWatcher != null) {
//                journalWatcher.stop();
//                journalWatcher = null;
//            }
            LogiLED.LogiLedShutdown();
            enabled = false;
        }
    }
}
