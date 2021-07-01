package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;
import org.junit.jupiter.api.Test;

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

}
