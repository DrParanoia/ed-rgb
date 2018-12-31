package com.bmc.elite.animations;

import com.bmc.elite.KeyTools;
import com.bmc.elite.mappings.Colors;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyAnimator {

    HashMap<Integer, AnimatedKey> keysToAnimate;

    public int msDuration;
    public boolean infinite;

    AnimationCallback animationCallback;
    boolean stopped = false;

    private KeyAnimator(int msDuration, boolean infinite, AnimationCallback animationCallback) {
        this.msDuration = msDuration;
        this.infinite = infinite;
        this.animationCallback = animationCallback;
    }
    public KeyAnimator(List<AnimatedKey> keysToAnimate, int msDuration, boolean infinite, AnimationCallback animationCallback) {
        this(msDuration, infinite, animationCallback);
        this.keysToAnimate = new HashMap<>();
        for(AnimatedKey animatedKey : keysToAnimate) {
            this.keysToAnimate.put(animatedKey.key, animatedKey);
        }
    }
    public KeyAnimator(List<AnimatedKey> keysToAnimate, int msDuration, boolean infinite) {
        this(keysToAnimate, msDuration, infinite, null);
    }

    public KeyAnimator(HashMap<Integer, AnimatedKey> keysToAnimate, int msDuration, boolean infinite, AnimationCallback animationCallback) {
        this(msDuration, infinite, animationCallback);
        this.keysToAnimate = new HashMap<>(keysToAnimate);
    }
    public KeyAnimator(HashMap<Integer, AnimatedKey> keysToAnimate, int msDuration, boolean infinite) {
        this(keysToAnimate, msDuration, infinite, null);
    }

    public KeyAnimator(Integer[] keys, int startColor, int endColor, int msDuration, boolean infinite, AnimationCallback animationCallback) {
        this(msDuration, infinite, animationCallback);
        keysToAnimate = new HashMap<>();
        for(Integer key : keys) {
            this.keysToAnimate.put(key, new AnimatedKey(key, startColor, endColor));
        }
    }
    public KeyAnimator(Integer[] keys, int startColor, int endColor, int msDuration, boolean infinite) {
        this(keys, startColor, endColor, msDuration, infinite, null);
    }

    public void removeKey(Integer keyToRemove) {
        synchronized (KeyAnimator.class) {
            keysToAnimate.remove(keyToRemove);
        }
        if(keysToAnimate.isEmpty()) {
            stop();
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long startTime = System.currentTimeMillis();
                    long finishTime = startTime + msDuration;
                    long currentTime, deltaTime;
                    float animationStep;
                    boolean animationEnded = false, reversed = false;
                    AnimatedKey currentKey;
                    Color activeColor;

                    while(!stopped) {
                        currentTime = System.currentTimeMillis();

                        if(animationEnded) {
                            if(infinite) {
                                startTime = currentTime;
                                finishTime = startTime + msDuration;
                                reversed = !reversed;
                                animationEnded = false;
                            } else {
                                break;
                            }
                        }

                        deltaTime = currentTime - startTime;
                        animationStep = Math.min(1, (1F / msDuration) * deltaTime);

                        HashMap<Integer, AnimatedKey> currentlyAnimatedKeys;
                        synchronized (KeyAnimator.class) {
                            currentlyAnimatedKeys = new HashMap<>(keysToAnimate);
                        }
                        for(Map.Entry<Integer, AnimatedKey> keyEntry : currentlyAnimatedKeys.entrySet()) {
                            currentKey = keyEntry.getValue();
                            if(reversed) {
                                activeColor = new Color(ColorInterpolator.interpolate(animationStep, currentKey.endColor, currentKey.startColor));
                            } else {
                                activeColor = new Color(ColorInterpolator.interpolate(animationStep, currentKey.startColor, currentKey.endColor));
                            }
                            if(currentKey.logitechKey) {
                                KeyTools.setLightingForKeyWithKeyName(
                                    currentKey.key,
                                    Colors.colorToPercent(activeColor.getRed()),
                                    Colors.colorToPercent(activeColor.getGreen()),
                                    Colors.colorToPercent(activeColor.getBlue())
                                );
                            } else {
                                KeyTools.setLightingForKeyWithHidCode(
                                    currentKey.key,
                                    Colors.colorToPercent(activeColor.getRed()),
                                    Colors.colorToPercent(activeColor.getGreen()),
                                    Colors.colorToPercent(activeColor.getBlue())
                                );
                            }
                        }

                        if(currentTime >= finishTime) {
                            animationEnded = true;
                        } else {
                            Thread.sleep(1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(animationCallback != null) {
                        animationCallback.onFinish(stopped);
                    }
                }
            }
        }).start();
    }

    public void stop() {
        synchronized (KeyAnimator.class) {
            keysToAnimate.clear();
        }
        stopped = true;
    }
}
