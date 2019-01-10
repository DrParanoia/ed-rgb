package com.bmc.elite.animations;

public class AnimatedKey {

    Boolean logitechKey;
    Integer key;
    Integer startColor;
    Integer endColor;

    public AnimatedKey(Integer key, Integer startColor, Integer endColor) {
        this(key, startColor, endColor, false);
    }
    public AnimatedKey(Integer key, Integer startColor, Integer endColor, Boolean logitechKey) {
        this.key = key;
        this.startColor = startColor;
        this.endColor = endColor;
        this.logitechKey = logitechKey;
    }
}
