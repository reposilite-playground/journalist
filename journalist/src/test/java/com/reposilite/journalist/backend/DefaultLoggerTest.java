package com.reposilite.journalist.backend;

import com.reposilite.journalist.Channel;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class DefaultLoggerTest {

    private static final class TestLogger extends DefaultLogger {

        private final Stack<String> messages = new Stack<>();

        TestLogger() {
            super(Channel.ALL);
        }

        @Override
        protected void internalLog(Channel channel, String message) {
            messages.push(message);
        }

    }

    private final TestLogger logger = new TestLogger();

    @Test
    void shouldNotSupportLoggingToAllChannel() {
        assertThrows(IllegalStateException.class, () -> logger.log(Channel.ALL, "should throw"));
    }

    @Test
    void shouldPrintStacktrace() {
        logger.exception(new Exception("TestException"));
        assertTrue(logger.messages.stream().anyMatch(line -> line.contains(Exception.class.getName() + ": TestException")));
    }

    // Support '%' character
    // ~ https://github.com/reposilite-playground/journalist/issues/15
    @Test
    void shouldPrintPercentageChar() {
        logger.info("%");
    }

}