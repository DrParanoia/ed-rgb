package com.bmc.elite.callbacks;

import com.bmc.elite.journal.JournalEvent;

public interface JournalCallback {
    void journalChanged(JournalEvent journalEvent);
}
