package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;
import net.dzikoysk.dynamiclogger.Logger;

import java.util.Arrays;
import java.util.Collection;

/**
 * Use various logger implementations at the same time.
 * AggregatedLogger listens for {@link net.dzikoysk.dynamiclogger.Channel#ALL} messages.
 */
public class AggregatedLogger extends DefaultLogger {

    private final Collection<? extends Logger> loggers;

    public AggregatedLogger(Collection<? extends Logger> loggers) {
        super(Channel.ALL);
        this.loggers = loggers;
    }

    public AggregatedLogger(Logger... loggers) {
        this(Arrays.asList(loggers));
    }

    @Override
    protected void internalLog(Channel channel, String message) {
        for (Logger logger : loggers) {
            logger.log(channel, message);
        }
    }

}
