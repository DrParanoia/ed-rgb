package com.bmc.elite.journal.events;

import com.bmc.elite.journal.JournalEvent;

public class Fileheader extends JournalEvent {
    public int part;
    public String language;
    public String gameversion;
    public String build;
}
