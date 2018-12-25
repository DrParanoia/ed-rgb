package com.bmc.elite;

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
    boolean eliteInFocus = false;

    public static final int PROCESS_WATCH_TIMEOUT = 200;
    public static final String ELITE_EXECUTABLE_CHECK = File.separator + "EliteDangerous64.exe";

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
                        if(activeWindowExe.endsWith(ELITE_EXECUTABLE_CHECK)) {
                            if(Application.DEBUG) System.out.println("Elite is in focus, starting highlighting");
                            eliteInFocus = true;
                            // Wait for logitech app to load any existing game profile
                            Thread.sleep(250);
                            eliteLed.enable();
                        } else {
                            if(Application.DEBUG && eliteInFocus) System.out.println("Elite lost focus, stopping highlighting");
                            eliteInFocus = false;
                            eliteLed.disable();
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
