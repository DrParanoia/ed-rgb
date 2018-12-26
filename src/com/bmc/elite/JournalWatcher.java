package com.bmc.elite;

import com.bmc.elite.callbacks.JournalCallback;
import com.bmc.elite.config.Application;
import com.bmc.elite.models.JournalEvent;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JournalWatcher {
    static Gson gson = new Gson();

    String currentLogFile = "";
    FileWatcher journalFileWatcher = null;
    int lastEventLine = -1;

    public JournalWatcher(JournalCallback journalCallback) {
        File lastLogFile = FileUtils.lastFileModified(Application.FRONTIER_DIRECTORY_PATH, ".log");

        if(lastLogFile != null) {
            String lastLogFilePath = lastLogFile.getAbsolutePath();
            if(!currentLogFile.equals(lastLogFilePath)) {
                currentLogFile = lastLogFilePath;
                stop();
                if(currentLogFile != null) {
                    if(Application.DEBUG) LogUtils.log("Watching journal file: " + currentLogFile);
                    lastEventLine = -1;
                    journalCallback.journalChanged(getNewJournalEvents());
                    journalFileWatcher = new FileWatcher(
                            currentLogFile,
                            changedFile -> {
                                List<JournalEvent> newJournalEvents = getNewJournalEvents();
                                if(!newJournalEvents.isEmpty()) {
                                    journalCallback.journalChanged(newJournalEvents);
                                }
                            }
                    );
                }
            }
        }
    }

    public List<JournalEvent> getNewJournalEvents() {
        List<JournalEvent> journalEvents = new ArrayList<>();

        if(currentLogFile != null) {
            FileUtils.readFile(currentLogFile, (lineNumber, line) -> {
                if(lineNumber > lastEventLine) {
                    journalEvents.add(gson.fromJson(line, JournalEvent.class));
                    lastEventLine = lineNumber;
                }
            });
        }

        return journalEvents;
    }

    public void stop() {
        if(journalFileWatcher != null) {
            journalFileWatcher.stop();
            journalFileWatcher = null;
        }
    }
}
