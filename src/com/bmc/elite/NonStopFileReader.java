package com.bmc.elite;

import com.bmc.elite.callbacks.FileReadCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class NonStopFileReader {

    public enum ReadMode {
        TAIL, TAIL_END, FULL
    };

    private final int READ_DELAY_MS = 3;

    private FileWatcher fileWatcher = null;
    private String filename, lineValue;
    private File file;
    private FileReadCallback readCallback;
    private boolean startAtTheEnd, useWatchService;
    private int currentLine = 0;
    private long lastModified = 0;

    private boolean stopped = false;

    private ReadMode readMode;

    private BufferedReader bufferedReader;

    public NonStopFileReader(String filename, FileReadCallback callback) {
        this(filename, callback, ReadMode.TAIL);
    }
    public NonStopFileReader(String filename, FileReadCallback callback, ReadMode readMode) {
        this(filename, callback, readMode, true);
    }
    public NonStopFileReader(String filename, FileReadCallback callback, ReadMode readMode, boolean useWatchService) {
        this.filename = filename;
        this.readCallback = callback;
        this.readMode = readMode;
        this.useWatchService = useWatchService;
        openFile();
    }

    private void createReader() {
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            bufferedReader = new BufferedReader(new FileReader(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFile() {
        try {
            createReader();
            file = new File(filename);

            if(readMode == ReadMode.TAIL_END) {
                while(bufferedReader.readLine() != null);
            }

            lastModified = file.lastModified();

            if(useWatchService) {
                readFile();
                fileWatcher = new FileWatcher(filename, changedFile -> readFile());
            } else {
                new Thread(this::readInALoop).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readInALoop() {
        try {
            while (true) {
                if(stopped) {
                    break;
                }

                if(readMode == ReadMode.FULL && lastModified != file.lastModified()) {
                    createReader();
                    lastModified = file.lastModified();
                }

                lineValue = bufferedReader.readLine();
                if(lineValue != null) {
                    readCallback.onRead(currentLine++, lineValue);
                } else {
                    Thread.sleep(READ_DELAY_MS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        try {
            if(readMode == ReadMode.FULL) {
                createReader();
            }
            while ((lineValue = bufferedReader.readLine()) != null) {
                readCallback.onRead(0, lineValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        stopped = true;
        currentLine = 0;
        if(fileWatcher != null) {
            fileWatcher.stop();
            fileWatcher = null;
        }
        if(bufferedReader != null) {
            try {
                bufferedReader.close();
                bufferedReader = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
