package com.bmc.elite;

import com.bmc.elite.gui.MainWindow;

public class Main {
    public static void main(String[] args) {
        boolean useWindow = true;
        boolean alwaysHighlight = false;
        for (String s: args) {
            if (s.equalsIgnoreCase("-nowindow")) {
                useWindow = false;
            } else if (s.equalsIgnoreCase("-alwayshighlight")) {
                alwaysHighlight = true;
            }
        }
        new MainWindow(useWindow, alwaysHighlight);
    }
}
