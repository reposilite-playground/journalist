package net.dzikoysk.dynamiclogger.slf4j;

import net.dzikoysk.dynamiclogger.Channel;
import net.dzikoysk.dynamiclogger.ChannelIntention;
import net.dzikoysk.dynamiclogger.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

final class Slf4jLoggerTest {

    private final Logger logger = new Slf4jLogger(LoggerFactory.getLogger("DynamicLogger"));

    @AfterEach
    public void cleanup() {
        DynamicLoggerWriter.getMessages().clear();
    }

    @Test
    void shouldRespectThreshold() {
        logger.setThreshold(Channel.WARN);
        logger.info("should not be logged");
        assertTrue(DynamicLoggerWriter.getMessages().isEmpty());

        logger.setThreshold(Channel.INFO);
        logger.info("should be logged");
        assertEquals(1, DynamicLoggerWriter.getMessages().size());
    }

    @ParameterizedTest
    @EnumSource(Slf4jChannel.class)
    void shouldProperlyMapChannelsToLevels(Slf4jChannel slf4jChannel) {
        logger.log(slf4jChannel.getChannel(), slf4jChannel.name());
        assertTrue(DynamicLoggerWriter.contain(slf4jChannel.getLevel().name() + " | " + slf4jChannel.name()));
    }

    @Test
    void shouldRedirectCustomChannelsToInfo() {
        logger.log(new Channel("Custom", 10, ChannelIntention.NEUTRAL), "Message");
        assertTrue(DynamicLoggerWriter.contain("INFO | Message"));
    }

}
