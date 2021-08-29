package com.reposilite.journalist.backend;

import com.reposilite.journalist.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AggregatedLoggerTest {

    @Test
    void shouldNotifyAllLoggers() {
        InMemoryLogger inMemoryLogger = new InMemoryLogger();

        Logger logger = new AggregatedLogger(
                inMemoryLogger,
                inMemoryLogger
        );

        logger.info("Message");
        assertEquals(2, inMemoryLogger.getMessages().size());
    }

}
