package com.bmc.elite.callbacks;

import com.bmc.elite.models.JournalEvent;

import java.util.List;

public interface JournalCallback {
    void journalChanged(List<JournalEvent> newEvents);
}
