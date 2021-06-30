package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;

import java.util.AbstractMap;
import java.util.Map.Entry;

/**
 * Stores all messages in memory as a queue with limit and does not print any message directly
 */
public class CachedLogger extends DefaultLogger {

    private final CircularBuffer<Entry<Channel, String>> messages;

    public CachedLogger(Channel threshold, int capacity) {
        super(threshold);

        this.messages = new CircularBuffer<>(capacity);
    }

    /**
     * Uses {@link net.dzikoysk.dynamiclogger.Channel#INFO} as a default threshold.
     *
     * @param capacity the capacity of message cache
     */
    public CachedLogger(int capacity) {
        this(Channel.ALL, capacity);
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
        for (int i = 0; i < getMessages().size(); i++) {
            if (!getMessages().peekAt(i).getValue().contains(message)) {
                continue;
            }

            return true;
        }

        return false;
    }

    /**
     * Get all stored messages
     *
     * @return the list of stored messages
     */
    public CircularBuffer<? extends Entry<Channel, String>> getMessages() {
        return messages;
    }

}
