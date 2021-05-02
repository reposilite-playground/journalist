package net.dzikoysk.dynamiclogger.slf4j;

import org.tinylog.Level;
import org.tinylog.core.LogEntry;
import org.tinylog.writers.AbstractFormatPatternWriter;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class DynamicLoggerWriter extends AbstractFormatPatternWriter {

    private static final Queue<Entry<Level, String>> MESSAGES = new ConcurrentLinkedQueue<>();

    public DynamicLoggerWriter(Map<String, String> properties) {
        super(properties);
    }

    @Override
    public void write(LogEntry logEntry) {
        MESSAGES.add(new AbstractMap.SimpleImmutableEntry<>(logEntry.getLevel(), render(logEntry)));
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
