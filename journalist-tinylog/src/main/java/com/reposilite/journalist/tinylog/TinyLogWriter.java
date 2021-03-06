package com.reposilite.journalist.tinylog;

import org.tinylog.core.LogEntry;
import org.tinylog.writers.AbstractFormatPatternWriter;
import panda.std.reactive.Subscriber;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class TinyLogWriter extends AbstractFormatPatternWriter {

    private static TinyLogWriter INSTANCE;
    private static final AtomicInteger ID_ASSIGNER = new AtomicInteger();
    private static final Map<Integer, Subscriber<LogEntry>> SUBSCRIBERS = new ConcurrentHashMap<>(2);

    public TinyLogWriter(Map<String, String> properties) {
        super(properties);
        INSTANCE = this;
    }

    public static int subscribe(Subscriber<LogEntry> subscriber) {
        int id = ID_ASSIGNER.incrementAndGet();
        SUBSCRIBERS.put(id, subscriber);
        return id;
    }

    public static boolean unsubscribe(int id) {
        return SUBSCRIBERS.remove(id) != null;
    }

    public static String renderEntry(LogEntry entry) {
        return INSTANCE.render(entry);
    }

    @Override
    public void write(LogEntry logEntry) {
        SUBSCRIBERS.forEach((id, subscriber) -> subscriber.onComplete(logEntry));
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
        SUBSCRIBERS.clear();
    }

}
