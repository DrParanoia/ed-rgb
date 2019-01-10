package com.bmc.elite;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogUtils {
    public static void log(String message) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

        String time = dateFormat.format(calendar.getTime());
        System.out.println(time + ": " + message);
    }
}
