package net.dzikoysk.dynamiclogger.slf4j;

import net.dzikoysk.dynamiclogger.Channel;

import java.util.Arrays;

public enum Slf4jChannel {

    FATAL(Channel.FATAL),
    ERROR(Channel.ERROR),
    WARN(Channel.WARN),
    INFO(Channel.INFO),
    DEBUG(Channel.DEBUG),
    TRACE(Channel.TRACE);

    private final Channel channel;

    Slf4jChannel(Channel channel) {
        this.channel = channel;
    }

    public static Slf4jChannel of(Channel channel) {
        return Arrays.stream(values())
                .filter(slf4jChannel -> slf4jChannel.channel.equals(channel))
                .findAny()
                .orElse(INFO);
    }

}
