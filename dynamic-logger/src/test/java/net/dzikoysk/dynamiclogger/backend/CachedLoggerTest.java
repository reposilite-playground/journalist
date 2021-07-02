package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import static net.dzikoysk.dynamiclogger.utils.EntryUtils.entryOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class CachedLoggerTest {

    @Test
    void shouldStoreMessages() {
        CachedLogger logger = new CachedLogger(Channel.INFO, 3);
        String testString = "INFO Message number ";

        for (int number = 0; number < 4; number++) {
            logger.info(testString + number);
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
        logger.info("INFO Hello");

        assertTrue(logger.find((channel, message) -> message.contains("Find")).isPresent());
        assertFalse(logger.find((channel, message) -> message.contains("Cheese")).isPresent());
    }

    @Test
    void shouldGetMessages() {
        CachedLogger logger = new CachedLogger(2);

        logger.info("INFO First message");
        logger.info("INFO Second message");
        logger.info("INFO Third message");

        List<Entry<Channel, String>> testList = new ArrayList<>();

        testList.add(entryOf(Channel.INFO, "INFO First message"));
        testList.add(entryOf(Channel.INFO, "INFO Second message"));
        testList.add(entryOf(Channel.INFO, "INFO Third message"));
        assertNotEquals(logger.getMessages(), testList);

        testList.remove(0);
        assertEquals(logger.getMessages(), testList);
    }

}
