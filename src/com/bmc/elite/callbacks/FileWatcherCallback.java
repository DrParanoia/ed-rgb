package com.bmc.elite.callbacks;

import java.nio.file.Path;

public interface FileWatcherCallback {
    void fileChanged(Path changedFile);
}
