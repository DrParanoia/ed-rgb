package com.bmc.elite;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.nio.file.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class FileWatcher {

    WatchService watchService;
    WatchKey directoryWatchKey;
    WatchKey watchServiceKey;

    public FileWatcher(final String filePath, final FileWatcherCallback callback) {
        watchFile(filePath, callback);
    }

    public void stop() {
        if(watchServiceKey != null) {
            watchServiceKey.cancel();
        }
        if(directoryWatchKey != null) {
            directoryWatchKey.cancel();
        }
        if(watchService != null) {
            try {
                watchService.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    public void watchFile(final String filePath, final FileWatcherCallback callback) {
        List<String> pathParts = new LinkedList<>(Arrays.asList(filePath.split(Pattern.quote(File.separator))));

        String filename = pathParts.remove(pathParts.size() - 1);
        String directory = String.join(File.separator, pathParts);

        Path statusFilePath = FileSystems.getDefault().getPath(directory);

        try {
            watchService = FileSystems.getDefault().newWatchService();
            directoryWatchKey = statusFilePath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            new Thread(() -> {
                try {
                    while (true) {
                        watchServiceKey = watchService.take();
                        for (WatchEvent<?> event : watchServiceKey.pollEvents()) {
                            final Path changed = (Path) event.context();
                            if (changed.startsWith(filename)) {
                                if(Application.DEBUG) System.out.println(filename + " has changed");
                                callback.fileChanged();
                            }
                        }
                        boolean valid = watchServiceKey.reset();
                        if (!valid) {
                            if(Application.DEBUG) System.out.println("Key has been unregistered");
                            break;
                        }
                    }
                } catch (ClosedWatchServiceException closedException) {
                    if(Application.DEBUG) System.out.println("Stopped watching " + filename);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
