package com.bmc.elite.animations;

import java.util.List;

public class AnimationQueueItem {
    public int duration = 200;
    public List<AnimatedKey> animatedKeyList;

    public AnimationQueueItem(int duration, List<AnimatedKey> animatedKeyList) {
        this.duration = duration;
        this.animatedKeyList = animatedKeyList;
    }
}
