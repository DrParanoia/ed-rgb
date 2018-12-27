package com.bmc.elite;

import com.bmc.elite.callbacks.JournalCallback;
import com.bmc.elite.config.Application;
import com.bmc.elite.models.JournalEvent;
import com.google.gson.Gson;

import java.io.File;

public class JournalWatcher {
    static Gson gson = new Gson();

    String currentLogFile = "";
    NonStopFileReader nonStopFileReader;

    public JournalWatcher(JournalCallback journalCallback) {
        File lastLogFile = FileUtils.lastFileModified(Application.FRONTIER_DIRECTORY_PATH, ".log");

        if(lastLogFile != null) {
            String lastLogFilePath = lastLogFile.getAbsolutePath();
            if(!currentLogFile.equals(lastLogFilePath)) {
                currentLogFile = lastLogFilePath;
                stop();
                if(currentLogFile != null) {
                    if(Application.DEBUG) LogUtils.log("Watching journal file: " + currentLogFile);

                    nonStopFileReader = new NonStopFileReader(currentLogFile, (lineNumber, lineValue) -> {
                        journalCallback.journalChanged(gson.fromJson(lineValue, JournalEvent.class));
                    }, NonStopFileReader.ReadMode.TAIL_END, false);
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
