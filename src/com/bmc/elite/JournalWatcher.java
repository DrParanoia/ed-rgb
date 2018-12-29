package com.bmc.elite;

import com.bmc.elite.callbacks.FileWatcherCallback;
import com.bmc.elite.callbacks.JournalCallback;
import com.bmc.elite.config.Application;
import com.bmc.elite.journal.UnknownEvent;
import com.bmc.elite.journal.JournalEvent;
import com.bmc.elite.journal.events.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;

public class JournalWatcher {
    static Gson journalGson;

    String currentLogFile = "";
    NonStopFileReader nonStopFileReader;
    JournalCallback journalCallback;
    FileWatcher journalDirectoryWatcher;

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
        this.journalCallback = journalCallback;
        initGson();
        start();
    }

    void start() {
        File latestLogFile = FileUtils.lastFileModified(Application.FRONTIER_DIRECTORY_PATH, ".log");

        if(latestLogFile != null) {
            String lastLogFilePath = latestLogFile.getAbsolutePath();
            if(!currentLogFile.equals(lastLogFilePath)) {
                stop(false);
                currentLogFile = lastLogFilePath;
                nonStopFileReader = new NonStopFileReader(currentLogFile, (lineNumber, lineValue) -> {
                    try {
                        JournalEvent newEvent = journalGson.fromJson(lineValue, JournalEvent.class);
                        if(newEvent.event == null) {
                            if(Application.DEBUG) LogUtils.log("Enum missing for: " + newEvent.getClass().getSimpleName());
                        } else {
                            journalCallback.journalChanged(newEvent);
                        }
                    } catch (JsonParseException e) {
                        UnknownEvent unknownEvent = journalGson.fromJson(lineValue, UnknownEvent.class);
                        if(Application.DEBUG) LogUtils.log("Unknown event: " + unknownEvent.event);
                    }
                }, NonStopFileReader.ReadMode.TAIL_END, false);

                if(journalDirectoryWatcher == null) {
                    journalDirectoryWatcher = new FileWatcher(
                        Application.FRONTIER_DIRECTORY_PATH,
                        new FileWatcherCallback() {
                            @Override
                            public void fileChanged(Path changedFile) {
                                if(Application.DEBUG) LogUtils.log("New file created: " + changedFile);
                                start();
                            }
                        },
                        StandardWatchEventKinds.ENTRY_CREATE
                    );
                }

                if(Application.DEBUG) LogUtils.log("Watching journal file: " + currentLogFile);
            }
        }
    }

    void stop() {
        stop(true);
    }
    void stop(boolean stopDirectoryWatcher) {
        if(nonStopFileReader != null) {
            nonStopFileReader.stop();
            nonStopFileReader = null;
        }
        if(journalDirectoryWatcher != null) {
            journalDirectoryWatcher.stop();
            journalDirectoryWatcher = null;
        }
        if(Application.DEBUG) LogUtils.log("Stopped watching journal file: " + currentLogFile);
    }
}
