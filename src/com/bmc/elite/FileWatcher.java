package com.bmc.elite;

import java.io.File;
import java.nio.file.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class FileWatcher {
    public static void watchFile(final String filePath, final FileWatcherCallback callback) {

        new Thread(() -> {
            List<String> pathParts = new LinkedList<>(Arrays.asList(filePath.split(Pattern.quote(File.separator))));

            String filename = pathParts.remove(pathParts.size() - 1);
            String directory = String.join(File.separator, pathParts);

            Path statusFilePath = FileSystems.getDefault().getPath(directory);

            try {
                final WatchService watchService = FileSystems.getDefault().newWatchService();
                final WatchKey watchKey = statusFilePath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
                while (true) {
                    final WatchKey wk = watchService.take();
                    for (WatchEvent<?> event : wk.pollEvents()) {
                        final Path changed = (Path) event.context();
                        if (changed.startsWith(filename)) {
                            System.out.println(filename + " has changed");
                            callback.fileChanged();
                        }
                    }
                    boolean valid = wk.reset();
                    if (!valid) {
                        System.out.println("Key has been unregistered");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
