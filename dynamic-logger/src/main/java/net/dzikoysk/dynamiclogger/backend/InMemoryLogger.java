package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Stores all messages in memory as a queue and does not print any message directly.
 */
public class InMemoryLogger extends DefaultLogger {

    private final Queue<Entry<Channel, String>> messages = new ConcurrentLinkedQueue<>();

    public InMemoryLogger(Channel threshold) {
        super(threshold);
    }

    /**
     * Uses {@link net.dzikoysk.dynamiclogger.Channel#INFO} as a default threshold.
     */
    public InMemoryLogger() {
        this(Channel.ALL);
    }

    @Override
    protected void internalLog(Channel channel, String message) {
        messages.add(new AbstractMap.SimpleImmutableEntry<>(channel, message));
    }

    /**
     * Check if one of the stored messages contain the given text
     *
     * @param message the text to search for
     * @return true if any stored message contains the given value, otherwise false
     */
    public boolean contains(String message) {
        return getMessages().stream().anyMatch(entry -> entry.getValue().contains(message));
    }

    /**
     * Get all stored messages
     *
     * @return the list of stored messages
     */
    public Queue<? extends Entry<Channel, String>> getMessages() {
        return messages;
    }

}
