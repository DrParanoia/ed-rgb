package com.bmc.elite;

import com.bmc.elite.gui.MainWindow;

public class Main {
    public static void main(String[] args) {
        boolean useWindow = true;
        for (String s: args) {
            if (s.equalsIgnoreCase("-nowindow")) {
                useWindow = false;
            }
        }
        new MainWindow(useWindow);
    }
}
