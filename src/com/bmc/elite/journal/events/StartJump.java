package com.bmc.elite.journal.events;

import com.bmc.elite.journal.JournalEvent;
import com.bmc.elite.journal.enums.JumpType;

public class StartJump extends JournalEvent {
    public JumpType JumpType;
    public String StarSystem;
    public long SystemAddress;
    public String StarClass;
}
