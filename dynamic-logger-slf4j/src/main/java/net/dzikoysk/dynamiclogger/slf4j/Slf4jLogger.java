package net.dzikoysk.dynamiclogger.slf4j;

import net.dzikoysk.dynamiclogger.Channel;
import net.dzikoysk.dynamiclogger.backend.DefaultLogger;
import org.slf4j.Logger;

public class Slf4jLogger extends DefaultLogger {

    private final Logger logger;

    public Slf4jLogger(Logger logger, Channel threshold) {
        super(threshold);
        this.logger = logger;
    }

    public Slf4jLogger(Logger logger) {
        this(logger, Channel.ALL);
    }

    @Override
    protected void internalLog(Channel channel, String message) {
        switch (Slf4jChannel.of(channel)) {
            case FATAL:
            case ERROR:
                logger.error(message);
                break;
            case WARN:
                logger.warn(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case DEBUG:
                logger.debug(message);
                break;
            case TRACE:
                logger.trace(message);
                break;
            default:
                throw new IllegalStateException("Unsupported channel: " + channel);
        }
    }

}
