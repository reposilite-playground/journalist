package net.dzikoysk.dynamiclogger;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

final class LoggerTest {

    private static final class TestLogger implements Logger {
        private final Stack<String> messages = new Stack<>();

        @Override
        public Logger log(Channel channel, String message) {
            messages.push(channel.getName() + " | " + message);
            return this;
        }

        @Override
        public Logger exception(Channel channel, Throwable throwable) {
            messages.push(channel.getName() + " | " + throwable.getMessage());
            return this;
        }

        @Override
        public Logger setThreshold(Channel threshold) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        public PrintStream toPrintStream(Channel channel) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        public Logger getLogger() {
            return this;
        }
    }

    @Test
    void shouldProperlyMapUtilityMethodsToChannels() {
        TestLogger logger = new TestLogger();

        logger.trace("Trace");
        assertEquals("trace | Trace", logger.messages.peek());

        logger.debug("Debug");
        assertEquals("debug | Debug", logger.messages.peek());

        logger.info("Info");
        assertEquals("info | Info", logger.messages.peek());

        logger.warn("Warn");
        assertEquals("warn | Warn", logger.messages.peek());

        logger.error("Error");
        assertEquals("error | Error", logger.messages.peek());

        logger.fatal("Fatal");
        assertEquals("fatal | Fatal", logger.messages.peek());

        logger.exception(new Exception("TestException"));
        assertEquals("error | TestException", logger.messages.peek());
    }

}
