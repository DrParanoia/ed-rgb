package com.bmc.elite.journal;

import com.bmc.elite.KeyColorService;
import com.bmc.elite.journal.enums.FighterStatus;

public class JournalStatus {
    private static volatile JournalStatus instance = null;

    private FighterStatus fighterStatus = FighterStatus.None;

    public static synchronized JournalStatus getInstance() {
        if(instance == null) {
            instance = new JournalStatus();
        }
        return instance;
    }

    private JournalStatus() {}

    public FighterStatus getFighterStatus() {
        return fighterStatus;
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
        }
    }
}
