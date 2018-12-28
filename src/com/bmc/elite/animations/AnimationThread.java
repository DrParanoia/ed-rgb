package com.bmc.elite.animations;

public class AnimationThread {

    AnimationRunnable animationRunnable;
    public AnimationThread(AnimationRunnable animationRunnable) {
        this(animationRunnable, false);
    }
    public AnimationThread(AnimationRunnable animationRunnable, boolean autostart) {
        this.animationRunnable = animationRunnable;
        if(autostart) {
            start();
        }
    }

    public void start() {
        new Thread(animationRunnable).start();
    }
    public void stop() {
        this.animationRunnable.stop();
    }
}
