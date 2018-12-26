package com.bmc.elite;

import com.bmc.elite.callbacks.FileLineCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;

public class FileUtils {
    public static String readFile(String filename) {
        return readFile(filename, null);
    }
    public static String readFile(String filename, FileLineCallback lineCallback) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                int lineNumber = 0;
                while (line != null) {
                    if(lineCallback != null) lineCallback.gotLine(lineNumber++, line);
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                result = sb.toString();
            } finally {
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static File lastFileModified(String dir) {
        return lastFileModified(dir, null);
    }
    public static File lastFileModified(String dir, String extension) {
        File fl = new File(dir);
        File[] files = fl.listFiles(file -> file.isFile() && (extension == null || file.getName().endsWith(extension)));
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }
}
