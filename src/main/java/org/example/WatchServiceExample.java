package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatchServiceExample {
    private final WatchService watcher;
    private Map<WatchKey, Path> dirWatchers;

    // creates a Watch Service and registers the given directory
    public WatchServiceExample(Path dir) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.dirWatchers = new HashMap<WatchKey, Path>();
        scanAndRegisterDirectories(dir);
    }

    // register the given directory with watch service
    private void registerDirWatchers(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_MODIFY, ENTRY_CREATE,
                ENTRY_DELETE);
        dirWatchers.put(key, dir);
    }

    // register the given directory and all its sub directories,with watchers
    private void scanAndRegisterDirectories(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerDirWatchers(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    // Process all events for key queued to watchers

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void processEvents() {
        while (true) {
            WatchKey key; // wait for key to be signaled
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }
            Path dir = dirWatchers.get(key);
            if (dir == null) continue;

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
                Path name = ((WatchEvent<Path>) event).context();
                Path child = dir.resolve(name);
                System.out.format("%s: %s\n", event.kind().name(), child); // print out event

                // if directory is created then register it and its sub directories

                if (kind == ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child))
                            scanAndRegisterDirectories(child);
                    } catch (IOException x) {
                    }
                } else if (kind.equals(ENTRY_DELETE)) {
                    if (Files.isDirectory(child)) {
                        dirWatchers.remove(key);
                    }
                }
            }
            //reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                dirWatchers.remove(key);
                if (dirWatchers.isEmpty())
                    break; // all directories are inaccessible
            }
        }
    }

}
