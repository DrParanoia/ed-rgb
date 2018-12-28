package com.bmc.elite.animations;

import com.bmc.elite.LedTools;
import com.bmc.elite.LogUtils;
import com.bmc.elite.config.LedKeys;
import com.bmc.elite.lists.LogitechKeysList;
import com.bmc.elite.mappings.Colors;
import com.bmc.elite.mappings.Events;
import com.bmc.elite.models.JournalEvent;
import com.logitech.gaming.LogiLED;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.CubicCurve;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class AnimationHelper {

    private static final int MIN_ANIMATION_DELAY_MS = 1;
    private static final int MAX_ANIMATION_STEPS = 100;

    private HashMap<Integer, AnimationState> animatingKeys = new HashMap<>();

    FSDAnimator fsdAnimator;

    private int animationCounter = 0;

    private static AnimationHelper instance = null;

    public static synchronized AnimationHelper getInstance() {
        if(instance == null) {
            instance = new AnimationHelper();
        }
        return instance;
    }

    private AnimationHelper() {
        fsdAnimator = new FSDAnimator(this);
    }

    public void processJournalEvent(JournalEvent journalEvent) {
        fsdAnimator.processJournalEvent(journalEvent);
    }

    public boolean isAnimating() {
        return animationCounter > 0;
    }
    public void increaseAnimationCount() {
        animationCounter++;
    }
    public void decreaseAnimationCount() {
        animationCounter = Math.min(0, animationCounter - 1);
    }

    public void stopKeyAnimation(int key) {
        if(animatingKeys.containsKey(key)) {
            animatingKeys.remove(key).animationThread.stop();
        }
    }
    public AnimationState startKeyAnimation(AnimationState animationState) {
        if(animatingKeys.containsKey(animationState.key)) {
            AnimationState currentAnimationState = animatingKeys.get(animationState.key);
            if(currentAnimationState.equals(animationState)) {
                return currentAnimationState;
            } else {
                stopKeyAnimation(animationState.key);
            }
        }

        animatingKeys.put(animationState.key, animationState);
        animationState.animationThread.start();
        return animationState;
    }

    public AnimationState pulseKey(final int key, Integer[] startColor, Integer[] endColor, int duration, boolean infinite) {
        Color start = new Color(
            Colors.percentToColor(startColor[0]),
            Colors.percentToColor(startColor[1]),
            Colors.percentToColor(startColor[2])
        );
        Color end = new Color(
            Colors.percentToColor(endColor[0]),
            Colors.percentToColor(endColor[1]),
            Colors.percentToColor(endColor[2])
        );
        return pulseKey(key, start.getRGB(), end.getRGB(), duration, infinite);
    }
    public AnimationState pulseKey(final int key, final Integer startColor, final Integer endColor, int duration, boolean infinite) {
        LogUtils.log("Starting pulse " + key);
        AnimationState animationState = new AnimationState(key, startColor, endColor, duration, infinite, new AnimationThread(new AnimationRunnable() {
            @Override
            public void run() {
                try {
                    long startTime = System.currentTimeMillis();
                    long finishTime = startTime + duration;
                    long currentTime, deltaTime;
                    float animationStep;
                    boolean animationEnded = false;
                    Integer color1 = startColor, color2 = endColor, tmpColor;
                    Color activeColor;

                    while(!stopped) {
                        currentTime = System.currentTimeMillis();
                        if(animationEnded) {
                            if(infinite) {
                                startTime = currentTime;
                                finishTime = startTime + duration;
                                tmpColor = color1;
                                color1 = color2;
                                color2 = tmpColor;
                                animationEnded = false;
                            } else {
                                break;
                            }
                        }
                        deltaTime = currentTime - startTime;
                        animationStep = Math.min(1, (1F / duration) * deltaTime);
                        activeColor = new Color(ColorInterpolator.interpolate(animationStep, color1, color2));

                        LogiLED.LogiLedSetLightingForKeyWithKeyName(
                            key,
                            Colors.colorToPercent(activeColor.getRed()),
                            Colors.colorToPercent(activeColor.getGreen()),
                            Colors.colorToPercent(activeColor.getBlue())
                        );

                        if(currentTime >= finishTime) {
                            animationEnded = true;
                        }
                        Thread.sleep(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    stopKeyAnimation(key);
                }
            }
        }));

        return startKeyAnimation(animationState);
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
