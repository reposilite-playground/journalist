package com.reposilite.journalist.backend;

import com.reposilite.journalist.Channel;
import panda.std.reactive.Publisher;
import panda.std.reactive.Subscriber;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import static com.reposilite.journalist.utils.EntryUtils.entryOf;

public class PublisherLogger extends DefaultLogger implements Publisher<Integer, Entry<Channel, String>> {

    private final Map<Integer, Subscriber<? super Entry<Channel, String>>> subscribers = new ConcurrentHashMap<>(2);
    private int id = 0;

    public PublisherLogger(Channel threshold) {
        super(threshold);
    }

    @Override
    protected void internalLog(Channel channel, String message) {
        Entry<Channel, String> entry = entryOf(channel, message);

        for (Subscriber<? super Entry<Channel, String>> subscriber : subscribers.values()) {
            subscriber.onComplete(entry);
        }
    }

    @Override
    public Integer subscribe(Subscriber<? super Entry<Channel, String>> subscriber) {
        int subscriberId = id++;
        subscribers.put(subscriberId, subscriber);
        return subscriberId;
    }

    public boolean unsubscribe(Integer subscriberId) {
        return subscribers.remove(subscriberId) != null;
    }

}
