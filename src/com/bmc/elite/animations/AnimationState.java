package com.bmc.elite.animations;

public class AnimationState {
    public int key;
    public Integer startColor;
    public Integer endColor;
    public int duration;
    public boolean infinite;
    public AnimationThread animationThread;

    public AnimationState(int key, Integer startColor, Integer endColor, int duration, boolean infinite, AnimationThread animationThread) {
        this.key = key;
        this.startColor = startColor;
        this.endColor = endColor;
        this.duration = duration;
        this.infinite = infinite;
        this.animationThread = animationThread;
    }

    public boolean equals(AnimationState animationState) {
        return
            this.key == animationState.key
            && this.startColor.equals(animationState.startColor)
            && this.endColor.equals(animationState.endColor)
            && this.duration == animationState.duration
            && this.infinite == animationState.infinite;
    }
}
