package com.bmc.elite.animations;

import com.bmc.elite.KeyTools;
import com.bmc.elite.journal.enums.JumpType;
import com.bmc.elite.journal.events.StartJump;
import com.bmc.elite.lists.LogitechKeysList;
import com.bmc.elite.mappings.Colors;
import com.bmc.elite.journal.JournalEvent;

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
            case StartJump:
                StartJump startJump = (StartJump) journalEvent;
                chargingFSD = true;
                if(startJump.JumpType == JumpType.Hyperspace) {
                    starType = startJump.StarClass.toLowerCase();
                } else {
                    starType = null;
                }
                FSDCountdown();
                break;
            case SupercruiseEntry:
                chargingFSD = false;
                break;
            case SupercruiseExit:
                chargingFSD = false;
                break;
            case FSDJump:
                chargingFSD = false;
                stopHyperspaceAnimation();
                break;
            case Music:
                if(chargingFSD) {
                    chargingFSD = false;
                    playHyperspaceAnimation();
                }
                break;
        }
    }

    public void playHyperspaceAnimation() {
        animationHelper.playFromFile("hyperspace_2.eft", 6, true);
    }
    public void stopHyperspaceAnimation() {
        animationHelper.stopPlayingFile("hyperspace.eft");

        String starFileName = "star_entry_" + starType + ".eft";
        if(getClass().getResource("/eft/" + starFileName) != null) {
            animationHelper.playFromFile(starFileName);
        } else {
            animationHelper.playFromFile("star_entry.eft");
        }
    }

    public void FSDCountdown() {
        animationHelper.increaseAnimationCount();
        List<Integer> remainingLogitechKeys = new LogitechKeysList();
        for(Integer logitechKey : remainingLogitechKeys) {
            KeyTools.setKeyFromColorArray(logitechKey, Colors.NONE);
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
