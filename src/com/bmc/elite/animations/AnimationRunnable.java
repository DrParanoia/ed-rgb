package com.bmc.elite.animations;

public abstract class AnimationRunnable implements Runnable {
    boolean stopped = false;
    public void stop() {
        stopped = true;
    }
}
