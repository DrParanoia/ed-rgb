package com.bmc.elite.journal.events;

import com.bmc.elite.journal.JournalEvent;
import com.bmc.elite.journal.LoadoutModule;

import java.util.List;

public class Loadout extends JournalEvent {
    public String Ship;
    public int ShipID;
    public String ShipName;
    public String ShipIdent;
    public long HullValue;
    public long ModulesValue;
    public double HullHealth;
    public long Rebuy;
    public List<LoadoutModule> Modules;
}
