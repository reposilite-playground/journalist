package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiPredicate;

/**
 * Stores all messages in memory as a queue with limit and does not print any message directly.
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
     * @param capacity the capacity of messages cache
     */
    public CachedLogger(int capacity) {
        this(Channel.ALL, capacity);
    }

    @Override
    protected void internalLog(Channel channel, String message) {
        messages.add(new AbstractMap.SimpleImmutableEntry<>(channel, message));
    }

    /**
     * Check if one of the stored messages contain the given text.
     *
     * @param message the text to search for
     * @return true if any stored message contains the given value, otherwise false
     */
    public boolean contains(String message) {
        return find((channel, channelMessage) -> channelMessage.contains(message)).isPresent();
    }

    /**
     * Find entry with message and channel related to it.
     *
     * @param filter the predicate for the channel or the message of the entry
     * @return the entry of the given predicate
     */
    public Optional<Entry<Channel, String>> find(BiPredicate<Channel, String> filter) {
        return messages.find(entry -> filter.test(entry.getKey(), entry.getValue()));
    }

    /**
     * Get all stored messages.
     *
     * @return the list of stored messages
     */
    public List<? extends Entry<Channel, String>> getMessages() {
        return messages.getValues();
    }

}
