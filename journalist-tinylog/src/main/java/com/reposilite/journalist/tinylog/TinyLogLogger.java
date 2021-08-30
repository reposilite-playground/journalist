package com.reposilite.journalist.tinylog;

import com.reposilite.journalist.Channel;
import com.reposilite.journalist.Journalist;
import com.reposilite.journalist.backend.DefaultLogger;
import org.tinylog.core.LogEntry;

public class TinyLogLogger extends DefaultLogger {

    private final Journalist consumer;
    private final int subscriberId;

    public TinyLogLogger(Channel threshold, Journalist consumer) {
        super(threshold);
        this.consumer = consumer;
        this.subscriberId = TinyLogWriter.subscribe(this::internalLog);
    }

    private void internalLog(LogEntry entry) {
        internalLog(
                TinyLogLevel.toSlf4jChannel(entry.getLevel()).getChannel(),
                entry.toString()
        );
    }

    @Override
    protected void internalLog(Channel channel, String message) {
        consumer.getLogger().log(channel, message);
    }

    public void close() {
        TinyLogWriter.unsubscribe(subscriberId);
    }

    public int getSubscriberId() {
        return subscriberId;
    }

}
