package com.bmc.elite.journal;

import com.bmc.elite.KeyColorService;
import com.bmc.elite.journal.enums.FighterStatus;
import com.bmc.elite.journal.events.Loadout;

public class JournalStatus {
    private static volatile JournalStatus instance = null;

    private FighterStatus fighterStatus = FighterStatus.None;



    private boolean hasChaff = false;
    private boolean hasHeatSink = false;
    private boolean hasShieldCellBank = false;

    public static synchronized JournalStatus getInstance() {
        if(instance == null) {
            instance = new JournalStatus();
        }
        return instance;
    }

    private JournalStatus() {}

    public FighterStatus getFighterStatus() {
        return this.fighterStatus;
    }
    public boolean hasChaff() {
//        return this.hasChaff;
        return true;
    }
    public boolean hasHeatSink() {
//        return this.hasHeatSink;
        return true;
    }
    public boolean hasShieldCellBank() {
//        return this.hasShieldCellBank;
        return true;
    }

    private void setModulesFromLoadout(Loadout loadout) {
        boolean hasChaff = false;
        boolean hasHeatSink = false;
        boolean hasShieldCellBank = false;
        for(LoadoutModule module : loadout.Modules) {
            if(module.Item.startsWith("Hpt_ChaffLauncher_")) {
                hasChaff = true;
            } else if(module.Item.startsWith("Hpt_HeatSinkLauncher_")) {
                hasHeatSink = true;
            } else if(module.Item.startsWith("Int_ShieldCellBank_")) {
                hasShieldCellBank = true;
            }
        }

        this.hasChaff = hasChaff;
        this.hasHeatSink = hasHeatSink;
        this.hasShieldCellBank = hasShieldCellBank;
    }

    private void setFighterStatus(FighterStatus fighterStatus) {
        setFighterStatus(fighterStatus, true);
    }
    private void setFighterStatus(FighterStatus fighterStatus, boolean updateStatus) {
        if(this.fighterStatus != fighterStatus) {
            this.fighterStatus = fighterStatus;
            KeyColorService.getInstance().updateStatus();
        }
    }

    public void processEvent(JournalEvent newJournalEvent) {
        processEvent(newJournalEvent, true);
    }
    public void processEvent(JournalEvent newJournalEvent, boolean updateStatus) {
        switch (newJournalEvent.event) {
            case LaunchFighter:
                setFighterStatus(FighterStatus.Launched, updateStatus);
                break;
            case FighterDestroyed:
            case DockFighter:
                setFighterStatus(FighterStatus.None, updateStatus);
                break;
            case Loadout:
                setModulesFromLoadout((Loadout) newJournalEvent);
                break;
        }
    }
}
