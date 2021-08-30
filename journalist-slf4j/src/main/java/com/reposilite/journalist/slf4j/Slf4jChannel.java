package com.reposilite.journalist.slf4j;

import com.reposilite.journalist.Channel;
import org.slf4j.event.Level;

public enum Slf4jChannel {

    FATAL(Channel.FATAL, Level.ERROR),
    ERROR(Channel.ERROR, Level.ERROR),
    WARN(Channel.WARN, Level.WARN),
    INFO(Channel.INFO, Level.INFO),
    DEBUG(Channel.DEBUG, Level.DEBUG),
    TRACE(Channel.TRACE, Level.TRACE);

    private static final Slf4jChannel[] COPY_OF_VALUES = values();

    private final Channel channel;
    private final Level level;

    Slf4jChannel(Channel channel, Level level) {
        this.channel = channel;
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public Channel getChannel() {
        return channel;
    }

    public static Slf4jChannel of(Channel channel) {
        for (Slf4jChannel slf4jChannel : COPY_OF_VALUES) {
            if (slf4jChannel.getChannel().equals(channel)) {
                return slf4jChannel;
            }
        }

        System.err.println("[Journalist#Internals] Unknown channel: " + channel);
        return INFO;
    }

    public static Slf4jChannel of(Level level) {
        for (Slf4jChannel channel : COPY_OF_VALUES) {
            if (channel.level == level) {
                return channel;
            }
        }

        System.err.println("[Journalist#Internals] Unknown level: " + level);
        return INFO;
    }

}
