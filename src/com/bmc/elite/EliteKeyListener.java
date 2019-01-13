package com.bmc.elite;

import com.bmc.elite.mappings.KeyMaps;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.*;

public class EliteKeyListener implements NativeKeyListener {

    private static final int KEY_DETECT_DELAY_MS = 0;
    private HashMap<Integer, Timer> keyEventTimers = new HashMap<>();
    private List<Integer> pressedKeys = new ArrayList<>();

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

    private void keyPressed(NativeKeyEvent nativeKeyEvent) {
        LogUtils.log("Key pressed: " + nativeKeyEvent.paramString() + " " + KeyMaps.getEliteKeyFromVirtual(nativeKeyEvent.getRawCode()));
    }
    private void keyReleased(NativeKeyEvent nativeKeyEvent) {
        LogUtils.log("Key released: " + nativeKeyEvent.paramString() + " " + KeyMaps.getEliteKeyFromVirtual(nativeKeyEvent.getRawCode()));
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
