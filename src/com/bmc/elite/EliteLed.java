package com.bmc.elite;

import com.bmc.elite.callbacks.JournalCallback;
import com.bmc.elite.config.Application;
import com.bmc.elite.config.LedKeys;
import com.bmc.elite.lists.LogitechKeysList;
import com.bmc.elite.mappings.Colors;
import com.bmc.elite.models.JournalEvent;
import com.logitech.gaming.LogiLED;

import java.util.List;

public class EliteLed {

    boolean enabled = false;
    FileWatcher statusFileWatcher;
    FileWatcher bindingsFileWatcher;
    JournalWatcher journalWatcher;

    int jumpState = 0;

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
                            if(journalEvent.event.equals("FSDTarget")) {
                                jumpState = 1;
                            } else if(journalEvent.event.equals("StartJump")) {
                                if(jumpState == 1) {
                                    jumpState = 2;
                                }
                            } else if(journalEvent.event.equals("FSDJump")) {
                                jumpState = 0;
                            }
                        }
                        if(jumpState == 2) {
                            LedTools.setKeyPulseFromColorArrays(LedKeys.NUM_NINE, Colors.SHIP_STUFF, Colors.DEFENCE, 300, true);
                            LedTools.setKeyPulseFromColorArrays(LedKeys.NUM_SEVEN, Colors.SHIP_STUFF, Colors.DEFENCE, 300, true);
                            LedTools.setKeyPulseFromColorArrays(LedKeys.NUM_EIGHT, Colors.SHIP_STUFF, Colors.DEFENCE, 300, true);
                        } else {
                            LedTools.stopKeyEffects(LedKeys.NUM_NINE);
                            LedTools.stopKeyEffects(LedKeys.NUM_SEVEN);
                            LedTools.stopKeyEffects(LedKeys.NUM_EIGHT);
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
            if(journalWatcher != null) {
                journalWatcher.stop();
                journalWatcher = null;
            }
            LogiLED.LogiLedShutdown();
            enabled = false;
        }
    }
}
