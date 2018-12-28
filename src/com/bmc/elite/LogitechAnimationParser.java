package com.bmc.elite;

import com.bmc.elite.animations.AnimatedKey;
import com.bmc.elite.animations.AnimationQueueItem;
import com.bmc.elite.animations.PulseParams;
import com.bmc.elite.mappings.LogitechAnimationKeys;
import com.bmc.elite.models.LogitechAnimation;
import com.bmc.elite.models.LogitechAnimationTransition;
import com.google.gson.Gson;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogitechAnimationParser {

    static Gson gson = new Gson();

    final static int LOGITECH_ANIMATION_MULTIPLIER = 5;

    static HashMap<String, List<AnimationQueueItem>> cache = new HashMap<>();

    public static List<AnimationQueueItem> parseFile(String filename) {
        return parseFile(filename, true);
    }
    public static List<AnimationQueueItem> parseFile(String filename, boolean fromCache) {
        if(fromCache && cache.containsKey(filename)) {
            return cache.get(filename);
        }

        String animationJson = FileUtils.readFile(filename);
        LogitechAnimation logitechAnimation = gson.fromJson(animationJson, LogitechAnimation.class);

        List<AnimationQueueItem> pulseParamSteps = new ArrayList<>();

        LogitechAnimationTransition currentTransition;

        Map<Integer, Integer> previousKeyStates = new HashMap<>();

        Map<String, String> currentKeyMap;
        Integer currentKey, currentColor, currentKeyMapType;
        for(int i = 0, length = logitechAnimation.transition_list.size(); i < length; i++) {
            currentTransition = logitechAnimation.transition_list.get(i);
            int duration = currentTransition.length * LOGITECH_ANIMATION_MULTIPLIER;

            List<AnimatedKey> currentAnimatedKeys = new ArrayList<>();
            for(Map.Entry<String, Map<String, String>> stateEntry : currentTransition.state.entrySet()) {
                currentKeyMap = stateEntry.getValue();
                currentKeyMapType = Integer.valueOf(stateEntry.getKey());
                for(Map.Entry<String, String> keyEntry : currentKeyMap.entrySet()) {
                    if(currentKeyMapType == 1) {
                        currentKey = Integer.valueOf(keyEntry.getKey(), 16);
                    } else {
                        currentKey = LogitechAnimationKeys.ANIMATOR_KEYS.get(currentKeyMapType).get(Integer.valueOf(keyEntry.getKey()));
                    }

                    currentColor = Color.decode(keyEntry.getValue()).getRGB();

                    if(previousKeyStates.containsKey(currentKey) && !currentColor.equals(previousKeyStates.get(currentKey))) {
                        currentAnimatedKeys.add(new AnimatedKey(
                            currentKey, previousKeyStates.get(currentKey), currentColor, currentKeyMapType != 1
                        ));
                    }
                    previousKeyStates.put(currentKey, currentColor);
                }
            }

            if(!currentAnimatedKeys.isEmpty()) {
                pulseParamSteps.add(new AnimationQueueItem(
                    duration,
                    currentAnimatedKeys
                ));
            }
        }

        cache.put(filename, pulseParamSteps);
        return pulseParamSteps;
    }
}
