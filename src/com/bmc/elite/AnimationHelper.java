package com.bmc.elite;

import com.bmc.elite.config.LedKeys;
import com.bmc.elite.lists.LogitechKeysList;
import com.bmc.elite.mappings.Colors;
import com.bmc.elite.mappings.Events;
import com.bmc.elite.models.JournalEvent;
import com.logitech.gaming.LogiLED;

import java.util.List;

public class AnimationHelper {

    boolean chargingFSD = false;

    private static int animationCounter = 0;

    public void processJournalEvent(JournalEvent journalEvent) {
        switch(journalEvent.event) {
            case Events.StartJump:
                chargingFSD = true;
                FSDCountdown();
                break;
            case Events.SupercruiseEntry:
                chargingFSD = false;
                break;
            case Events.SupercruiseExit:
                chargingFSD = false;
                break;
            case Events.FSDJump:
                chargingFSD = false;
                stopHyperspaceAnimation();
                break;
            case Events.Music:
                if(chargingFSD) {
                    chargingFSD = false;
                    playHyperspaceAnimation();
                }
                break;
        }
    }

    public static boolean isAnimating() {
        return animationCounter > 0;
    }
    private static void increaseAnimationCount() {
        animationCounter++;
    }
    private static void decreaseAnimationCount() {
        animationCounter = Math.min(0, animationCounter - 1);
    }

    public void playHyperspaceAnimation() {
        increaseAnimationCount();
        LedTools.setKeyPulseFromColorArrays(LedKeys.NUM_NINE, Colors.SHIP_STUFF, Colors.DEFENCE, 300, true);
        LedTools.setKeyPulseFromColorArrays(LedKeys.NUM_SEVEN, Colors.SHIP_STUFF, Colors.DEFENCE, 300, true);
        LedTools.setKeyPulseFromColorArrays(LedKeys.NUM_EIGHT, Colors.SHIP_STUFF, Colors.DEFENCE, 300, true);
    }
    public void stopHyperspaceAnimation() {
        decreaseAnimationCount();
        LedTools.stopKeyEffects(LedKeys.NUM_NINE);
        LedTools.stopKeyEffects(LedKeys.NUM_SEVEN);
        LedTools.stopKeyEffects(LedKeys.NUM_EIGHT);
    }

    public void FSDCountdown() {
        increaseAnimationCount();
        List<Integer> remainingLogitechKeys = new LogitechKeysList();
        for(Integer logitechKey : remainingLogitechKeys) {
            LedTools.setKeyFromColorArray(logitechKey, Colors.NONE);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Need this sleep so that keys have time to dim
                    Thread.sleep(50);
                    pulseAnimation();
                    Thread.sleep(1000);
                    pulseAnimation();
                    Thread.sleep(1000);
                    pulseAnimation();
                    Thread.sleep(1000);
                    pulseAnimation();
                    Thread.sleep(1000);
                    pulseAnimation();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    decreaseAnimationCount();
                }
            }
        }).start();
    }

    public void pulseAnimation() {
        new Thread(() -> {
            try {
                int del = 120;
                int dim = 600;

                Integer[] color = new Integer[] {0, 100, 100};

                LedTools.setKeysPulseFromColorArrays(new int[] {
                    LogiLED.H
                }, color, Colors.NONE, dim, false);

                Thread.sleep(del);

                LedTools.setKeysPulseFromColorArrays(new int[] {
                    LogiLED.Y, LogiLED.G, LogiLED.B, LogiLED.N, LogiLED.J, LogiLED.U,
                }, color, Colors.NONE, dim, false);

                Thread.sleep(del);

                LedTools.setKeysPulseFromColorArrays(new int[] {
                    LogiLED.SIX, LogiLED.T, LogiLED.F, LogiLED.V, LogiLED.SPACE, LogiLED.M, LogiLED.K, LogiLED.I,
                    LogiLED.EIGHT, LogiLED.SEVEN,
                }, color, Colors.NONE, dim, false);

                Thread.sleep(del);

                LedTools.setKeysPulseFromColorArrays(new int[] {
                    LogiLED.F5, LogiLED.FIVE, LogiLED.R, LogiLED.D, LogiLED.C, LogiLED.F6, LogiLED.F7, LogiLED.NINE,
                    LogiLED.O, LogiLED.L, LogiLED.COMMA
                }, color, Colors.NONE, dim, false);

                Thread.sleep(del);

                LedTools.setKeysPulseFromColorArrays(new int[] {
                    LogiLED.F4, LogiLED.FOUR, LogiLED.R, LogiLED.S, LogiLED.X, LogiLED.LEFT_ALT, LogiLED.F8, LogiLED.ZERO,
                    LogiLED.P, LogiLED.SEMICOLON, LogiLED.PERIOD, LogiLED.RIGHT_ALT
                }, color, Colors.NONE, dim, false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
