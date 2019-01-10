package com.bmc.elite.animations;

public class PulseParams {
    public int key;
    public Integer startColor;
    public Integer endColor;
    public int duration;
    public boolean infinite;

    public PulseParams(int key, Integer startColor, Integer endColor, int duration, boolean infinite) {
        this.key = key;
        this.startColor = startColor;
        this.endColor = endColor;
        this.duration = duration;
        this.infinite = infinite;
    }

    public boolean equals(PulseParams pulseParams) {
        return this.key == pulseParams.key
            && this.startColor.equals(pulseParams.startColor)
            && this.endColor.equals(pulseParams.endColor)
            && this.duration == pulseParams.duration
            && this.infinite == pulseParams.infinite;
    }
}
