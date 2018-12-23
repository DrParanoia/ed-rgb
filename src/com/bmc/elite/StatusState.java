package com.bmc.elite;

public class StatusState {
    int[] flagsSet;
    int guiFocus = -1;
    int[] flagsNotSet;

    public StatusState(int[] flagsSet, int guiFocus, int[] flagsNotSet) {
        this.flagsSet = flagsSet;
        this.guiFocus = guiFocus;
        this.flagsNotSet = flagsNotSet;
    }
    public StatusState(int[] flagsSet, int[] flagsNotSet) {
        this(flagsSet, -1, flagsNotSet);
    }
    public StatusState(int[] flagsSet, int guiFocus) {
        this(flagsSet, guiFocus, new int[]{});
    }
    public StatusState(int[] flagsSet) {
        this(flagsSet, -1, new int[]{});
    }

    public boolean conditionSatisfied(long flags, int guiFocus) {
        if(this.guiFocus != -1 && this.guiFocus != guiFocus) {
            return false;
        }
        for(int flag : this.flagsSet) {
            if(!KeyColorService.isBitSet(flags, flag)) {
                return false;
            }
        }
        for(int flag : this.flagsNotSet) {
            if(KeyColorService.isBitSet(flags, flag)) {
                return false;
            }
        }

        return true;
    }
}
