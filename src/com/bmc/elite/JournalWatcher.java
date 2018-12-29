package com.bmc.elite;

import com.bmc.elite.callbacks.JournalCallback;
import com.bmc.elite.config.Application;
import com.bmc.elite.journal.GenericEvent;
import com.bmc.elite.journal.JournalEvent;
import com.bmc.elite.journal.events.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.File;

public class JournalWatcher {
    static Gson journalGson;

    String currentLogFile = "";
    NonStopFileReader nonStopFileReader;

    private void initGson() {
        RuntimeTypeAdapterFactory<JournalEvent> journalAdapterFactory = RuntimeTypeAdapterFactory.of(JournalEvent.class, "event", true)
            .registerSubtype(DockFighter.class)
            .registerSubtype(FighterDestroyed.class)
            .registerSubtype(FighterRebuilt.class)
            .registerSubtype(Fileheader.class)
            .registerSubtype(FSDJump.class)
            .registerSubtype(FSDTarget.class)
            .registerSubtype(LaunchFighter.class)
            .registerSubtype(Music.class)
            .registerSubtype(StartJump.class)
            .registerSubtype(SupercruiseEntry.class)
            .registerSubtype(SupercruiseExit.class);
        journalGson = new GsonBuilder()
            .registerTypeAdapterFactory(journalAdapterFactory)
            .create();
    }

    public JournalWatcher(JournalCallback journalCallback) {
        initGson();

        File lastLogFile = FileUtils.lastFileModified(Application.FRONTIER_DIRECTORY_PATH, ".log");

        if(lastLogFile != null) {
            String lastLogFilePath = lastLogFile.getAbsolutePath();
            if(!currentLogFile.equals(lastLogFilePath)) {
                currentLogFile = lastLogFilePath;
                stop();
                if(currentLogFile != null) {
                    if(Application.DEBUG) LogUtils.log("Watching journal file: " + currentLogFile);

                    nonStopFileReader = new NonStopFileReader(currentLogFile, (lineNumber, lineValue) -> {
                        try {
                            JournalEvent newEvent = journalGson.fromJson(lineValue, JournalEvent.class);
                            if(newEvent.event == null) {
                                if(Application.DEBUG) LogUtils.log("Add enum for: " + lineValue);
                            } else {
                                journalCallback.journalChanged(newEvent);
                            }
                        } catch (JsonParseException e) {
                            GenericEvent genericEvent = journalGson.fromJson(lineValue, GenericEvent.class);
                            if(Application.DEBUG) LogUtils.log("Unknown event: " + genericEvent.event);
                        }
                    }, NonStopFileReader.ReadMode.TAIL, false);
                }
            }
        }
    }

    void stop() {
        if(nonStopFileReader != null) {
            nonStopFileReader.stop();
            nonStopFileReader = null;
        }
    }
}
