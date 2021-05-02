package net.dzikoysk.dynamiclogger.slf4j;

import net.dzikoysk.dynamiclogger.Channel;
import org.slf4j.event.Level;

import java.util.Arrays;

public enum Slf4jChannel {

    FATAL(Channel.FATAL, Level.ERROR),
    ERROR(Channel.ERROR, Level.ERROR),
    WARN(Channel.WARN, Level.WARN),
    INFO(Channel.INFO, Level.INFO),
    DEBUG(Channel.DEBUG, Level.DEBUG),
    TRACE(Channel.TRACE, Level.TRACE);

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
        return Arrays.stream(values())
                .filter(slf4jChannel -> slf4jChannel.channel.equals(channel))
                .findAny()
                .orElse(INFO);
    }

}
