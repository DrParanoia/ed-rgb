package com.bmc.elite;

import com.bmc.elite.callbacks.StatusConditionCallback;
import com.bmc.elite.models.Status;

public class StatusState {
    int[] flagsSet;
    int guiFocus = -1;
    int[] flagsNotSet;
    StatusConditionCallback conditionCallback;

    public StatusState(StatusConditionCallback conditionCallback) {
        this(null, null, conditionCallback);
    }
    public StatusState(int[] flagsSet, int guiFocus, int[] flagsNotSet) {
        this(flagsSet, -1, flagsNotSet, null);
    }
    public StatusState(int[] flagsSet, int guiFocus, int[] flagsNotSet, StatusConditionCallback conditionCallback) {
        this.flagsSet = flagsSet;
        this.guiFocus = guiFocus;
        this.flagsNotSet = flagsNotSet;
        this.conditionCallback = conditionCallback;
    }

    public StatusState(int[] flagsSet, int[] flagsNotSet) {
        this(flagsSet, flagsNotSet, null);
    }
    public StatusState(int[] flagsSet, int[] flagsNotSet, StatusConditionCallback conditionCallback) {
        this(flagsSet, -1, flagsNotSet, conditionCallback);
    }
    public StatusState(int[] flagsSet, int guiFocus) {
        this(flagsSet, guiFocus, new int[]{}, null);
    }
    public StatusState(int[] flagsSet) {
        this(flagsSet, -1, new int[]{}, null);
    }

    public boolean conditionSatisfied(Status status) {
        return conditionSatisfied(status.Flags, status.GuiFocus);
    }
    public boolean conditionSatisfied(long flags, int guiFocus) {
        if(this.guiFocus != -1 && this.guiFocus != guiFocus) {
            return false;
        }
        if(this.flagsSet != null) {
            for(int flag : this.flagsSet) {
                if(!KeyColorService.isBitSet(flags, flag)) {
                    return false;
                }
            }
        }
        if(this.flagsNotSet != null) {
            for(int flag : this.flagsNotSet) {
                if(KeyColorService.isBitSet(flags, flag)) {
                    return false;
                }
            }
        }

        if(conditionCallback != null) {
            return conditionCallback.checkCondition();
        }

        return true;
    }
}
