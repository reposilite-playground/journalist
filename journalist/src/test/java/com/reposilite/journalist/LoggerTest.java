package com.reposilite.journalist;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

final class LoggerTest {

    private static final class TestLogger implements Logger {
        private final Stack<Object> messages = new Stack<>();

        @Override
        public Logger log(Channel channel, Object message, Object... arguments) {
            message = Objects.toString(message);
            messages.push(channel.getName() + " | " + String.format((message.toString()).replace("{}", "%s"), arguments));
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

        logger.trace("Trace {}-{}", "trace", 1);
        assertEquals("trace | Trace trace-1", logger.messages.peek());

        logger.debug("Debug {}-{}", "debug", 1);
        assertEquals("debug | Debug debug-1", logger.messages.peek());

        logger.info("Info {}-{}", "info", 1);
        assertEquals("info | Info info-1", logger.messages.peek());

        logger.warn("Warn {}-{}", "warn", 1);
        assertEquals("warn | Warn warn-1", logger.messages.peek());

        logger.error("Error {}-{}", "error", 1);
        assertEquals("error | Error error-1", logger.messages.peek());

        logger.fatal("Fatal {}-{}", "fatal", 1);
        assertEquals("fatal | Fatal fatal-1", logger.messages.peek());

        logger.exception(new Exception("TestException"));
        assertEquals("error | TestException", logger.messages.peek());
    }

}
