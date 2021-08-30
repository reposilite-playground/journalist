package com.reposilite.journalist.slf4j;

import com.reposilite.journalist.Channel;
import com.reposilite.journalist.ChannelIntention;
import com.reposilite.journalist.Logger;
import com.reposilite.journalist.tinylog.TinyLogWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class Slf4jLoggerTest {

    private final Logger logger = new Slf4jLogger(LoggerFactory.getLogger("SL4J"));
    private final List<String> messages = new ArrayList<>();

    @BeforeEach
    public void subscribe() {
        TinyLogWriter.subscribe(logEntry -> messages.add(logEntry.getLevel().name() + " | " + logEntry.getMessage()));
    }

    @Test
    void shouldRespectThreshold() {
        logger.setThreshold(Channel.WARN);
        logger.info("should not be logged");
        assertTrue(messages.isEmpty());

        logger.setThreshold(Channel.INFO);
        logger.info("should be logged");
        assertEquals(1, messages.size());
    }

    @ParameterizedTest
    @EnumSource(Slf4jChannel.class)
    void shouldProperlyMapChannelsToLevels(Slf4jChannel slf4jChannel) {
        logger.log(slf4jChannel.getChannel(), slf4jChannel.name());
        assertTrue(messages.contains(slf4jChannel.getLevel().name() + " | " + slf4jChannel.name()));
    }

    @Test
    void shouldRedirectCustomChannelsToInfo() {
        logger.log(new Channel("Custom", 10, ChannelIntention.NEUTRAL), "Message");
        assertTrue(messages.contains("INFO | Message"));
    }

}
