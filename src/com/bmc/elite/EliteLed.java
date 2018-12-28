package com.bmc.elite;

import com.bmc.elite.animations.AnimationHelper;
import com.bmc.elite.callbacks.JournalCallback;
import com.bmc.elite.config.Application;
import com.bmc.elite.models.JournalEvent;
import com.logitech.gaming.LogiLED;

public class EliteLed {

    boolean enabled = false;
    NonStopFileReader statusFileReader;
    FileWatcher bindingsFileWatcher;
    JournalWatcher journalWatcher;
    AnimationHelper animationHelper = AnimationHelper.getInstance();

    public void enable() {
        if(!enabled) {
            enabled = true;
            LogiLED.LogiLedInit();
            LogiLED.LogiLedSetTargetDevice(LogiLED.LOGI_DEVICETYPE_PERKEY_RGB);
            KeyColorService keyColorService = new KeyColorService();

            statusFileReader = new NonStopFileReader(
                Application.STATUS_FILE_PATH,
                (lineNumber, lineValue) -> keyColorService.updateStatus(lineValue), NonStopFileReader.ReadMode.FULL
            );

            bindingsFileWatcher = new FileWatcher(
                BindingParser.getBindingsFile().getAbsolutePath(),
                changedFile -> keyColorService.initColors(true)
            );
            if(journalWatcher == null) {
                journalWatcher = new JournalWatcher(new JournalCallback() {
                    @Override
                    public void journalChanged(JournalEvent journalEvent) {
                        if(Application.DEBUG) LogUtils.log("New event: " + journalEvent.event);
                        animationHelper.processJournalEvent(journalEvent);
                    }
                });
            }
        }
    }
    public void disable() {
        if(enabled) {
            if(statusFileReader != null) {
                statusFileReader.stop();
                statusFileReader = null;
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
