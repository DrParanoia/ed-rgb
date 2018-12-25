package com.bmc.elite;

import com.bmc.elite.gui.MainWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.io.File;
import java.io.PrintStream;

public class EliteProcessWatcherRunnable implements Runnable {

    WinDef.HWND activeWindow;
    String activeWindowExe = "";
    String previousActiveExe = "";
    EliteLed eliteLed;
    String lastFocusedProcess = "";

    public static final int PROCESS_WATCH_TIMEOUT = 200;
    public static final String ELITE_EXECUTABLE_CHECK = "EliteDangerous64.exe";
    public static final String JAVA_EXECUTABLE_CHECK = "java.exe";

    public EliteProcessWatcherRunnable() {
        eliteLed = new EliteLed();
    }

    @Override
    public void run() {
        while (true) {
            try {
                activeWindow = User32.INSTANCE.GetForegroundWindow();
                if(activeWindow != null) {
                    activeWindowExe = WindowUtils.getProcessFilePath(activeWindow);

                    if(!previousActiveExe.equals(activeWindowExe)) {
                        if(Application.DEBUG) System.out.println("Foreground process changed: " + activeWindowExe);
                        previousActiveExe = activeWindowExe;

                        if(activeWindowExe.endsWith(JAVA_EXECUTABLE_CHECK) && MainWindow.IN_FOCUS) {
                            lastFocusedProcess = JAVA_EXECUTABLE_CHECK;

                            if(Application.DEBUG) System.out.println("Highlighting app gained focus, starting highlighting");
                        } else if(activeWindowExe.endsWith(ELITE_EXECUTABLE_CHECK)) {
                            lastFocusedProcess = ELITE_EXECUTABLE_CHECK;

                            if(Application.DEBUG) System.out.println("Elite gained focus, starting highlighting");
                        } else if(!lastFocusedProcess.isEmpty()) {
                            lastFocusedProcess = "";
                            eliteLed.disable();
                            if(Application.DEBUG && !lastFocusedProcess.isEmpty()) System.out.println(lastFocusedProcess + " lost focus, stopping highlighting");
                        }

                        if(!lastFocusedProcess.isEmpty()) {
                            // Wait for logitech app to load any existing game profile
                            Thread.sleep(250);
                            eliteLed.enable();
                        }
                    }
                }
                Thread.sleep(PROCESS_WATCH_TIMEOUT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
