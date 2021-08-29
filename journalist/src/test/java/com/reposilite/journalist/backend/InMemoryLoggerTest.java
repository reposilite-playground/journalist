package com.reposilite.journalist.backend;

import com.reposilite.journalist.Channel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class InMemoryLoggerTest {

    private final InMemoryLogger logger = new InMemoryLogger(Channel.INFO);

    @Test
    void shouldStoreMessages() {
        logger.debug("DEBUG Should not store this message");
        logger.info("INFO Should store this message");

        assertFalse(logger.contains("DEBUG"));
        assertTrue(logger.contains("INFO"));
    }

}
