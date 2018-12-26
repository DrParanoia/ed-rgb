package com.bmc.elite;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUtils {
    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
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
}
