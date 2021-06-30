package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class CachedLoggerTest {

    private final CachedLogger logger = new CachedLogger(Channel.INFO, 3);

    @Test
    void shouldStoreMessages() {
        String testString = "INFO Message number ";

        for (int i = 0; i < 4; i++) {
            logger.info(testString + i);
        }

        Assertions.assertTrue(logger.contains(testString + "1"));
        Assertions.assertTrue(logger.contains(testString + "2"));
        Assertions.assertTrue(logger.contains(testString + "3"));
        Assertions.assertEquals(3, logger.getMessages().size());
    }

}
