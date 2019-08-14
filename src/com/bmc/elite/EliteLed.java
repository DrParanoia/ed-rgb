package com.bmc.elite;

import com.bmc.elite.animations.AnimationHelper;
import com.bmc.elite.callbacks.JournalCallback;
import com.bmc.elite.config.Application;
import com.bmc.elite.journal.JournalEvent;
import com.bmc.elite.listeners.EliteKeyListener;
import com.bmc.elite.listeners.EliteMouseListener;
import com.logitech.gaming.LogiLED;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EliteLed {

    public boolean enabled = false;
    NonStopFileReader statusFileReader;
    FileWatcher bindingsFileWatcher;
    JournalWatcher journalWatcher;
    AnimationHelper animationHelper = AnimationHelper.getInstance();
    KeyColorService keyColorService = KeyColorService.getInstance();

    EliteKeyListener eliteKeyListener = new EliteKeyListener();
    EliteMouseListener eliteMouseListener = new EliteMouseListener();

    public void enable() {
        if(!enabled) {
            enabled = true;
            LogiLED.LogiLedInit();
            LogiLED.LogiLedSetTargetDevice(LogiLED.LOGI_DEVICETYPE_PERKEY_RGB);

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

            try {
                Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
                logger.setLevel(Level.WARNING);
                logger.setUseParentHandlers(false);

                GlobalScreen.registerNativeHook();
                GlobalScreen.addNativeKeyListener(eliteKeyListener);
                GlobalScreen.addNativeMouseListener(eliteMouseListener);
            } catch (NativeHookException e) {
                LogUtils.log("There was a problem registering the native hook.");
                LogUtils.log(e.getMessage());
            }
        }
    }

    public void disable() {
        if(enabled) {
            try {
                GlobalScreen.removeNativeMouseListener(eliteMouseListener);
                GlobalScreen.removeNativeKeyListener(eliteKeyListener);
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e) {
                LogUtils.log("There was a problem unregistering the native hook.");
                LogUtils.log(e.getMessage());
            }

            if(statusFileReader != null) {
                statusFileReader.stop();
                statusFileReader = null;
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
