package com.bmc.elite.journal;

import java.util.List;

public class Faction {
    public String Name;
    public String FactionState;
    public String Government;
    public double Influence;
    public String Allegiance;
    public String Happiness;
    public String Happiness_Localised;
    public double MyReputation;

    public List<FactionState> ActiveStates;
    public List<FactionState> PendingStates;
}
