package com.bmc.elite.animations;

import com.bmc.elite.LogitechAnimationParser;
import com.bmc.elite.mappings.Colors;
import com.bmc.elite.journal.JournalEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimationHelper {

    private HashMap<Integer, KeyAnimator> animatingKeys = new HashMap<>();
    private HashMap<String, KeyAnimator> animationsInProgress = new HashMap<>();

    public FSDAnimator fsdAnimator;

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

    public void stopPlayingFile(String animationFileName) {
        String filename = "/eft/" + animationFileName;
        if(animationsInProgress.containsKey(filename)) {
            animationsInProgress.remove(filename).stop();
        }
    }
    public void playFromFile(String animationFileName) {
        playFromFile(animationFileName, false);
    }
    public void playFromFile(String animationFileName, boolean infinite) {
        String filename = "/eft/" + animationFileName;
        List<AnimationQueueItem> animationItems = LogitechAnimationParser.parseFile(getClass().getResource(filename).getPath());
        playPulseParamQueue(filename, animationItems, infinite);
    }
    public void playPulseParamQueue(String id, List<AnimationQueueItem> animationItems, boolean infinite) {
        playPulseParamQueue(id, animationItems, 0, infinite);
    }
    public void playPulseParamQueue(String id, List<AnimationQueueItem> animationItems, int position, boolean infinite) {
        AnimationQueueItem animationItem = animationItems.get(position);

        boolean finalPulse = position == animationItems.size() - 1;
        increaseAnimationCount();
        if(!finalPulse || infinite) {
            animationsInProgress.put(id, pulseKeys(animationItem.animatedKeyList, animationItem.duration, false, new AnimationCallback() {
                @Override
                public void onFinish(boolean wasStopped) {
                    if(!wasStopped) {
                        playPulseParamQueue(id, animationItems, finalPulse ? 0 : position + 1, infinite);
                    } else {
                        stopPlayingFile(id);
                    }
                    decreaseAnimationCount();
                }
            }));
        } else {
            animationsInProgress.put(id, pulseKeys(animationItem.animatedKeyList, animationItem.duration, false, new AnimationCallback() {
                @Override
                public void onFinish(boolean wasStopped) {
                    stopPlayingFile(id);
                    decreaseAnimationCount();
                }
            }));
        }
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
        animationCounter = Math.max(0, animationCounter - 1);
    }

    public void stopKeyAnimation(int key) {
        if(animatingKeys.containsKey(key)) {
            animatingKeys.remove(key).removeKey(key);
        }
    }

    public void startKeysAnimation(HashMap<Integer, AnimatedKey> animatedKeyList, KeyAnimator keyAnimator) {
        for(AnimatedKey animatedKey : animatedKeyList.values()) {
            stopKeyAnimation(animatedKey.key);
            animatingKeys.put(animatedKey.key, keyAnimator);
        }
        keyAnimator.start();
    }
    public void startKeysAnimation(List<AnimatedKey> animatedKeyList, KeyAnimator keyAnimator) {
        for(AnimatedKey animatedKey : animatedKeyList) {
            stopKeyAnimation(animatedKey.key);
            animatingKeys.put(animatedKey.key, keyAnimator);
        }
        keyAnimator.start();
    }

    public KeyAnimator pulseKey(final int key, Integer[] startColor, Integer[] endColor, int duration, boolean infinite) {
        return pulseKeys(new Integer[] {key}, startColor, endColor, duration, infinite, null);
    }
    public KeyAnimator pulseKeys(final Integer[] keys, Integer[] startColor, Integer[] endColor, int duration, boolean infinite, AnimationCallback animationCallback) {
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
        return pulseKeys(keys, start.getRGB(), end.getRGB(), duration, infinite, animationCallback);
    }

    public KeyAnimator pulseKey(final int key, Integer startColor, Integer endColor, int duration, boolean infinite) {
        return pulseKeys(new Integer[] {key}, startColor, endColor, duration, infinite, null);
    }
    public KeyAnimator pulseKeys(final Integer[] keys, final Integer startColor, final Integer endColor, int duration, boolean infinite, AnimationCallback animationCallback) {
        List<AnimatedKey> animatedKeyList = new ArrayList<>();
        for(Integer key : keys) {
            animatedKeyList.add(new AnimatedKey(key, startColor, endColor));
        }

        return pulseKeys(animatedKeyList, duration, infinite, animationCallback);
    }
    public KeyAnimator pulseKeys(List<AnimatedKey> animatedKeyList, int duration, boolean infinite) {
        return pulseKeys(animatedKeyList, duration, infinite, null);
    }
    public KeyAnimator pulseKeys(List<AnimatedKey> animatedKeyList, int duration, boolean infinite, AnimationCallback animationCallback) {
        KeyAnimator keyAnimator = new KeyAnimator(animatedKeyList, duration, infinite, animationCallback);
        startKeysAnimation(animatedKeyList, keyAnimator);

        return keyAnimator;
    }

    public KeyAnimator pulseKeys(HashMap<Integer, AnimatedKey> animatedKeyMap, int duration, boolean infinite) {
        return pulseKeys(animatedKeyMap, duration, infinite, null);
    }
    public KeyAnimator pulseKeys(HashMap<Integer, AnimatedKey> animatedKeyMap, int duration, boolean infinite, AnimationCallback animationCallback) {
        KeyAnimator keyAnimator = new KeyAnimator(animatedKeyMap, duration, infinite, animationCallback);
        startKeysAnimation(animatedKeyMap, keyAnimator);

        return keyAnimator;
    }
}
