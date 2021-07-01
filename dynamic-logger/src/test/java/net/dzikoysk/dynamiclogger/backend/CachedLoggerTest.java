package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import static org.junit.jupiter.api.Assertions.*;

final class CachedLoggerTest {

    private final CachedLogger logger = new CachedLogger(Channel.INFO, 3);

    @Test
    void shouldStoreMessages() {
        String testString = "INFO Message number ";

        for (int i = 0; i < 4; i++) {
            logger.info(testString + i);
        }

        assertFalse(logger.contains(testString + "0"));
        assertTrue(logger.contains(testString + "1"));
        assertTrue(logger.contains(testString + "2"));
        assertTrue(logger.contains(testString + "3"));
        assertEquals(3, logger.getMessages().size());
    }

    @Test
    void shouldFindMessage() {
        logger.info("INFO Find me!");

        assertTrue(logger.find((channel, message) -> message.contains("Find")).isPresent());
    }

    @Test
    void shouldGetMessages() {
        logger.info("INFO First message.");
        logger.info("INFO Second message.");

        List<Entry<Channel, String>> testList = new ArrayList<>();
        testList.add(new AbstractMap.SimpleImmutableEntry<>(Channel.INFO, "INFO First message."));
        testList.add(new AbstractMap.SimpleImmutableEntry<>(Channel.INFO, "INFO Second message."));

        assertEquals(logger.getMessages(), testList);
    }

}
