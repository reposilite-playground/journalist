package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import static org.junit.jupiter.api.Assertions.*;

final class CachedLoggerTest {
    @Test
    void shouldStoreMessages() {
        CachedLogger logger = new CachedLogger(Channel.INFO, 3);
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
        CachedLogger logger = new CachedLogger(2);

        logger.info("INFO Find me!");
        logger.info("INFO Hello.");

        assertTrue(logger.find((channel, message) -> message.contains("Find")).isPresent());
        assertFalse(logger.find((channel, message) -> message.contains("Cheese")).isPresent());
    }

    @Test
    void shouldGetMessages() {
        CachedLogger logger = new CachedLogger(3);

        logger.info("INFO First message.");
        logger.info("INFO Second message.");
        logger.info("INFO Third message.");
        logger.info("INFO Fourth message.");

        List<Entry<Channel, String>> testList = new ArrayList<>();

        testList.add(new AbstractMap.SimpleImmutableEntry<>(Channel.INFO, "INFO First message."));
        testList.add(new AbstractMap.SimpleImmutableEntry<>(Channel.INFO, "INFO Second message."));
        testList.add(new AbstractMap.SimpleImmutableEntry<>(Channel.INFO, "INFO Third message."));
        testList.add(new AbstractMap.SimpleImmutableEntry<>(Channel.INFO, "INFO Fourth message."));
        assertNotEquals(logger.getMessages(), testList);

        testList.remove(0);
        assertEquals(logger.getMessages(), testList);
    }

}
