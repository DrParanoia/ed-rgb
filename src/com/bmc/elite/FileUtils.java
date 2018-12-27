package com.bmc.elite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileUtils {
    public static String readFile(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append(System.lineSeparator());
                }
            } finally {
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
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
