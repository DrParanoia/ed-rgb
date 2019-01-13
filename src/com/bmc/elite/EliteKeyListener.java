package com.bmc.elite;

import com.bmc.elite.mappings.KeyMaps;
import com.bmc.elite.models.EliteBind;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.*;

public class EliteKeyListener implements NativeKeyListener {

    private static final int KEY_DETECT_DELAY_MS = 0;
    private HashMap<Integer, Timer> keyEventTimers = new HashMap<>();
    private static List<Integer> pressedKeys = new ArrayList<>();
    private static List<String> pressedEliteKeys = new ArrayList<>();

    private static List<String> pressedEliteModifiers = new ArrayList<>();

    public static List<String> allowedModifierBinds = new ArrayList<>();

    private KeyColorService keyColorService = KeyColorService.getInstance();

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

    public static boolean isVirtualKeyPressed(Integer virtualKey) {
        return pressedKeys.contains(virtualKey);
    }
    public static boolean isEliteKeyPressed(String eliteKey) {
        return pressedEliteKeys.contains(eliteKey);
    }

    private void keyPressed(NativeKeyEvent nativeKeyEvent) {
        String eliteKey = KeyMaps.getEliteKeyFromVirtual(nativeKeyEvent.getRawCode());
        if(eliteKey != null && eliteKey != "") {
            pressedEliteKeys.add(eliteKey);
        }

        if(BindingParser.allModifiers.contains(eliteKey)) {
            pressedEliteModifiers.add(eliteKey);
            Collections.sort(pressedEliteModifiers);
            generateAllowedModifierBinds();
        }

        keyColorService.updateStatus();
    }
    private void keyReleased(NativeKeyEvent nativeKeyEvent) {
        String eliteKey = KeyMaps.getEliteKeyFromVirtual(nativeKeyEvent.getRawCode());
        if(eliteKey != null && eliteKey != "") {
            pressedEliteKeys.remove(eliteKey);
        }

        if(pressedEliteModifiers.remove(eliteKey)) {
            Collections.sort(pressedEliteModifiers);
            generateAllowedModifierBinds();
        }

        keyColorService.updateStatus();
    }

    private void generateAllowedModifierBinds() {
        HashMap<String, List<EliteBind>> keyToBindMap = new HashMap<>();
        allowedModifierBinds.clear();
        for(EliteBind bindWithModifier : BindingParser.bindsWithModifiers) {
            if(!keyToBindMap.containsKey(bindWithModifier.eliteKey)) {
                keyToBindMap.put(bindWithModifier.eliteKey, new ArrayList<>());
            }

            if(bindWithModifier.allModifiersPressed()) {
                keyToBindMap.get(bindWithModifier.eliteKey).add(bindWithModifier);
            }
        }

        for(Map.Entry<String, List<EliteBind>> keyBindsEntry : keyToBindMap.entrySet()) {
            List<EliteBind> binds = keyBindsEntry.getValue();

            for(EliteBind bindWithModifier : binds) {
                String currentModifierString = bindWithModifier.getModifierString();

                boolean disabled = false;
                for(EliteBind bindWithModifierToCheck : binds) {
                    if(
                        bindWithModifier != bindWithModifierToCheck
                        && bindWithModifierToCheck.getModifierString().contains(currentModifierString)
                    ) {
                        disabled = true;
                        break;
                    }
                }

                if(!disabled) {
                    allowedModifierBinds.add(bindWithModifier.toString());
                }
            }
        }
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
