package com.bmc.elite.animations;

import com.bmc.elite.LedTools;
import com.bmc.elite.config.LedKeys;
import com.bmc.elite.lists.LogitechKeysList;
import com.bmc.elite.mappings.Colors;
import com.bmc.elite.mappings.Events;
import com.bmc.elite.models.JournalEvent;

import java.util.List;

public class FSDAnimator {

    boolean chargingFSD = false;
    AnimationHelper animationHelper;

    public FSDAnimator(AnimationHelper animationHelper) {
        this.animationHelper = animationHelper;
    }

    public void processJournalEvent(JournalEvent journalEvent) {
        switch(journalEvent.event) {
            case Events.StartJump:
                chargingFSD = true;
                FSDCountdown();
                break;
            case Events.SupercruiseEntry:
                chargingFSD = false;
                break;
            case Events.SupercruiseExit:
                chargingFSD = false;
                break;
            case Events.FSDJump:
                chargingFSD = false;
                stopHyperspaceAnimation();
                break;
            case Events.Music:
                if(chargingFSD) {
                    chargingFSD = false;
                    playHyperspaceAnimation();
                }
                break;
        }
    }

    public void playHyperspaceAnimation() {
        animationHelper.increaseAnimationCount();
        LedTools.setKeyPulseFromColorArrays(LedKeys.NUM_NINE, Colors.SHIP_STUFF, Colors.DEFENCE, 300, true);
        LedTools.setKeyPulseFromColorArrays(LedKeys.NUM_SEVEN, Colors.SHIP_STUFF, Colors.DEFENCE, 300, true);
        LedTools.setKeyPulseFromColorArrays(LedKeys.NUM_EIGHT, Colors.SHIP_STUFF, Colors.DEFENCE, 300, true);
    }
    public void stopHyperspaceAnimation() {
        animationHelper.decreaseAnimationCount();
        LedTools.stopKeyEffects(LedKeys.NUM_NINE);
        LedTools.stopKeyEffects(LedKeys.NUM_SEVEN);
        LedTools.stopKeyEffects(LedKeys.NUM_EIGHT);
    }

    public void FSDCountdown() {
        animationHelper.increaseAnimationCount();
        List<Integer> remainingLogitechKeys = new LogitechKeysList();
        for(Integer logitechKey : remainingLogitechKeys) {
            LedTools.setKeyFromColorArray(logitechKey, Colors.NONE);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Need this sleep so that keys have time to dim
                    Thread.sleep(50);
                    animationHelper.pulseAnimation();
                    Thread.sleep(1000);
                    animationHelper.pulseAnimation();
                    Thread.sleep(1000);
                    animationHelper.pulseAnimation();
                    Thread.sleep(1000);
                    animationHelper.pulseAnimation();
                    Thread.sleep(1000);
                    animationHelper.pulseAnimation();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    animationHelper.decreaseAnimationCount();
                }
            }
        }).start();
    }
}
