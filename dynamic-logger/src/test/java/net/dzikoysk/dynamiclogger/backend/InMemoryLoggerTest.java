package net.dzikoysk.dynamiclogger.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class InMemoryLoggerTest {

    private final InMemoryLogger logger = new InMemoryLogger();

    @Test
    void shouldStoreMessages() {
        logger.debug("DEBUG Should not store this message");
        logger.info("INFO Should store this message");

        Assertions.assertFalse(logger.contains("DEBUG"));
        Assertions.assertTrue(logger.contains("INFO"));
    }

}
