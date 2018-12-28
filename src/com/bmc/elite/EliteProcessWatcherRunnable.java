package com.bmc.elite;

import com.bmc.elite.config.Application;
import com.bmc.elite.gui.MainWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.io.File;
import java.util.regex.Pattern;

public class EliteProcessWatcherRunnable implements Runnable {

    WinDef.HWND activeWindow;
    String activeWindowExePath = "";
    String previousActiveExePath = "";
    String activeWindowExe = "";
    EliteLed eliteLed;
    String lastFocusedProcess = "";

    public static final int PROCESS_WATCH_TIMEOUT = 200;
    public static final String ELITE_EXECUTABLE_CHECK = "EliteDangerous64.exe";
    public static final String JAVA_EXECUTABLE_CHECK = "java.exe";
    public static final String JAVA_EXECUTABLE_CHECK_ALT = "javaw.exe";

    public EliteProcessWatcherRunnable() {
        eliteLed = new EliteLed();
    }

    @Override
    public void run() {
        String[] exePathParts;
        while (true) {
            try {
                activeWindow = User32.INSTANCE.GetForegroundWindow();
                if(activeWindow != null) {
                    activeWindowExePath = WindowUtils.getProcessFilePath(activeWindow);
                    exePathParts = activeWindowExePath.split(Pattern.quote(File.separator));
                    activeWindowExe = exePathParts[exePathParts.length - 1];

                    if(!previousActiveExePath.equals(activeWindowExePath)) {
                        if(Application.DEBUG) LogUtils.log("Foreground process changed: " + activeWindowExePath);
                        previousActiveExePath = activeWindowExePath;

                        if(
                            (
                                activeWindowExe.equals(JAVA_EXECUTABLE_CHECK)
                                || activeWindowExe.equals(JAVA_EXECUTABLE_CHECK_ALT)
                            )
                            && MainWindow.IN_FOCUS
                        ) {
                            if(Application.DEBUG) LogUtils.log("Highlighting app gained focus, starting highlighting");
                            lastFocusedProcess = activeWindowExe;
                        } else if(activeWindowExe.equals(ELITE_EXECUTABLE_CHECK)) {
                            if(Application.DEBUG) LogUtils.log("Elite gained focus, starting highlighting");
                            lastFocusedProcess = activeWindowExe;
                        } else if(!lastFocusedProcess.isEmpty()) {
                            if(Application.DEBUG && !lastFocusedProcess.isEmpty()) LogUtils.log(lastFocusedProcess + " lost focus, stopping highlighting");
                            lastFocusedProcess = "";
                            eliteLed.disable();
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
