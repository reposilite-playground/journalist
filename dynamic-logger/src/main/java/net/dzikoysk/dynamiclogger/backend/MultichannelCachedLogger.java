package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static net.dzikoysk.dynamiclogger.utils.EntryUtils.entryOf;

/**
 * Stores all messages in memory as a queue with limit of messages per channel and does not print any message directly.
 */
public class MultichannelCachedLogger extends DefaultLogger {

    private final Map<Channel, CircularBuffer<String>> messages;

    public MultichannelCachedLogger(Channel threshold, int capacity, Set<Channel> channels) {
        super(threshold);
        this.messages = createCacheMap(capacity, channels);
    }

    /**
     * Uses {@link net.dzikoysk.dynamiclogger.Channel#INFO} as a default threshold.
     *
     * @param capacity the capacity of messages cache
     * @param channels list of supported channels
     */
    public MultichannelCachedLogger(int capacity, Set<Channel> channels) {
        this(Channel.ALL, capacity, channels);
    }

    private Map<Channel, CircularBuffer<String>> createCacheMap(int capacity, Set<Channel> channels) {
        Map<Channel, CircularBuffer<String>> cache = new HashMap<>();
        channels.forEach(channel -> cache.put(channel, new CircularBuffer<>(capacity)));
        return cache;
    }

    @Override
    protected void internalLog(Channel channel, String message) {
        Optional.ofNullable(messages.get(channel))
                .map(cache -> cache.add(message))
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported channel: " + channel));
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
        for (Entry<Channel, CircularBuffer<String>> entry : messages.entrySet()) {
            Optional<String> result = entry.getValue().find(message -> filter.test(entry.getKey(), message));

            if (result.isPresent()) {
                return result.map(message -> entryOf(entry.getKey(), message));
            }
        }

        return Optional.empty();
    }

    /**
     * Get all stored messages.
     *
     * @return the list of stored messages
     */
    public Map<? extends Channel, ? extends List<String>> getMessages() {
        return messages.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, entry -> entry.getValue().getValues()));
    }

}
