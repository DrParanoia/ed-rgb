package com.bmc.elite;

import com.bmc.elite.mappings.KeyMaps;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.*;

public class EliteKeyListener implements NativeKeyListener {

    private static final int KEY_DETECT_DELAY_MS = 0;
    private HashMap<Integer, Timer> keyEventTimers = new HashMap<>();
    private List<Integer> pressedKeys = new ArrayList<>();
    private static List<String> pressedEliteKeys = new ArrayList<>();

    KeyColorService keyColorService = KeyColorService.getInstance();

    private boolean stopKeyTimer(int keyId) {
        if(keyEventTimers.containsKey(keyId)) {
            Timer timer = keyEventTimers.get(keyId);
            timer.cancel();
            timer.purge();
            keyEventTimers.remove(keyId);

            return true;
        }

        return false;
    }

    public static boolean isEliteKeyPressed(String eliteKey) {
        return pressedEliteKeys.contains(eliteKey);
    }

    private void keyPressed(NativeKeyEvent nativeKeyEvent) {
        String eliteKey = KeyMaps.getEliteKeyFromVirtual(nativeKeyEvent.getRawCode());
        if(eliteKey != null && eliteKey != "") {
            pressedEliteKeys.add(eliteKey);
        }

        LogUtils.log("Key pressed: " + nativeKeyEvent.paramString() + " " + eliteKey);
        keyColorService.updateStatus();
    }
    private void keyReleased(NativeKeyEvent nativeKeyEvent) {
        String eliteKey = KeyMaps.getEliteKeyFromVirtual(nativeKeyEvent.getRawCode());
        if(eliteKey != null && eliteKey != "") {
            pressedEliteKeys.remove(eliteKey);
        }

        LogUtils.log("Key released: " + nativeKeyEvent.paramString() + " " + eliteKey);
        keyColorService.updateStatus();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}

    @Override
    public void nativeKeyPressed(final NativeKeyEvent nativeKeyEvent) {
        final int keyId = nativeKeyEvent.getKeyCode();
        final Timer timer = new Timer();
        if(pressedKeys.contains(keyId)) {
            return;
        }

        // The delay is needed because otherwise the events will spam pressed/released all the time
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopKeyTimer(keyId);
                pressedKeys.add(keyId);
                keyPressed(nativeKeyEvent);
            }
        }, KEY_DETECT_DELAY_MS);
        keyEventTimers.put(keyId, timer);
    }

    @Override
    public void nativeKeyReleased(final NativeKeyEvent nativeKeyEvent) {
        Integer keyId = nativeKeyEvent.getKeyCode();
        if(!stopKeyTimer(keyId) && pressedKeys.remove(keyId)) {
            keyReleased(nativeKeyEvent);
        }
    }
}
