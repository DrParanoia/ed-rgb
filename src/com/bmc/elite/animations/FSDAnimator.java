package com.bmc.elite.animations;

import com.bmc.elite.LedTools;
import com.bmc.elite.config.LedKeys;
import com.bmc.elite.lists.LogitechKeysList;
import com.bmc.elite.mappings.Colors;
import com.bmc.elite.mappings.Events;
import com.bmc.elite.models.JournalEvent;

import java.util.Arrays;
import java.util.List;

public class FSDAnimator {

    boolean chargingFSD = false;
    AnimationHelper animationHelper;
    String starType = null;

    public FSDAnimator(AnimationHelper animationHelper) {
        this.animationHelper = animationHelper;
    }

    public void processJournalEvent(JournalEvent journalEvent) {
        switch(journalEvent.event) {
            case Events.StartJump:
                chargingFSD = true;
                if(journalEvent.JumpType.equals("Hyperspace")) {
                    starType = journalEvent.StarClass.toLowerCase();
                } else {
                    starType = null;
                }
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
        animationHelper.playFromFile("hyperspace.eft", true);
    }
    public void stopHyperspaceAnimation() {
        animationHelper.stopPlayingFile("hyperspace.eft");

        String starFileName = "start_entry_" + starType + ".eft";
        if(getClass().getResource("/eft/" + starFileName) != null) {
            animationHelper.playFromFile(starFileName);
        } else {
            animationHelper.playFromFile("start_entry.eft");
        }
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
                    animationHelper.playFromFile("scan_pulse.eft");
                    Thread.sleep(1000);
                    animationHelper.playFromFile("scan_pulse.eft");
                    Thread.sleep(1000);
                    animationHelper.playFromFile("scan_pulse.eft");
                    Thread.sleep(1000);
                    animationHelper.playFromFile("scan_pulse.eft");
                    Thread.sleep(1000);
                    animationHelper.playFromFile("scan_pulse.eft");
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
