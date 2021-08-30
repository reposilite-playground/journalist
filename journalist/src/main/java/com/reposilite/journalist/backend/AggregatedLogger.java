package com.reposilite.journalist.backend;

import com.reposilite.journalist.Channel;
import com.reposilite.journalist.Logger;

import java.util.Arrays;
import java.util.Collection;

/**
 * Use various logger implementations at the same time.
 * AggregatedLogger listens for {@link com.reposilite.journalist.Channel#ALL} messages.
 */
public class AggregatedLogger extends DefaultLogger {

    private final Collection<? extends Logger> loggers;

    public AggregatedLogger(Channel threshold, Collection<? extends Logger> loggers) {
        super(threshold);
        this.loggers = loggers;
    }

    public AggregatedLogger(Logger... loggers) {
        this(Channel.ALL, Arrays.asList(loggers));
    }

    @Override
    protected void internalLog(Channel channel, String message) {
        for (Logger logger : loggers) {
            logger.log(channel, message);
        }
    }

}
