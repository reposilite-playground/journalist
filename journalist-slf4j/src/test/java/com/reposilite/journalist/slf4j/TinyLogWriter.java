package com.reposilite.journalist.slf4j;

import org.tinylog.Level;
import org.tinylog.core.LogEntry;
import org.tinylog.writers.AbstractFormatPatternWriter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.reposilite.journalist.utils.EntryUtils.entryOf;

public final class TinyLogWriter extends AbstractFormatPatternWriter {

    private static final Queue<Entry<Level, String>> MESSAGES = new ConcurrentLinkedQueue<>();

    public TinyLogWriter(Map<String, String> properties) {
        super(properties);
    }

    @Override
    public void write(LogEntry logEntry) {
        MESSAGES.add(entryOf(logEntry.getLevel(), render(logEntry)));
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
        MESSAGES.clear();
    }

    public static boolean contain(String value) {
        return MESSAGES.stream().anyMatch(line -> line.getValue().trim().contains(value));
    }

    public static Queue<? extends Entry<Level, String>> getMessages() {
        return MESSAGES;
    }

}