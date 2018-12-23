package com.bmc.elite;

import com.logitech.gaming.LogiLED;

import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
        try {
            LogiLED.LogiLedInit();
            KeyColorService keyColorService = new KeyColorService();

            Path statusFilePath = FileSystems.getDefault().getPath(KeyColorService.FRONTIER_DIRECTORY_PATH);

            final WatchService watchService = FileSystems.getDefault().newWatchService();
            final WatchKey watchKey = statusFilePath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                final WatchKey wk = watchService.take();
                for (WatchEvent<?> event : wk.pollEvents()) {
                    final Path changed = (Path) event.context();
                    if (changed.startsWith(KeyColorService.STATUS_FILE_NAME)) {
                        System.out.println("Game status has changed");
                        keyColorService.setKeyColorFromStatus();
                    }
                }
                boolean valid = wk.reset();
                if (!valid) {
                    System.out.println("Key has been unregistered");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LogiLED.LogiLedShutdown();
        }
    }
}
