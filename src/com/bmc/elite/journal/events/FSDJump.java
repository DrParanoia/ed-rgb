package com.bmc.elite.journal.events;

import com.bmc.elite.journal.JournalEvent;

import java.util.List;

public class FSDJump extends JournalEvent {
    public String StarSystem;
    public long SystemAddress;
    public List<Double> StarPos;

    public String SystemAllegiance;
    public String SystemEconomy;
    public String SystemEconomy_Localised;
    public String SystemSecondEconomy;
    public String SystemSecondEconomy_Localised;
    public String SystemGovernment;
    public String SystemGovernment_Localised;
    public String SystemSecurity;
    public String SystemSecurity_Localised;

    public long Population;
    public double JumpDist;
    public double FuelUsed;
    public double FuelLevel;

    public String SystemFaction;
}
