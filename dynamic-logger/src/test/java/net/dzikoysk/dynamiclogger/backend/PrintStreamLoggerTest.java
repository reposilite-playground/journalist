package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Logger;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PrintStreamLoggerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final Logger logger = new PrintStreamLogger(new PrintStream(outContent), new PrintStream(errContent));

    @Test
    void shouldWriteNeutralMessagesToOut() {
        logger.info("Test");
        assertEquals("Test", outContent.toString().trim());
    }

    @Test
    void shouldWriteNegativeMessagesToErr() {
        logger.error("Test");
        assertEquals("Test", errContent.toString().trim());
    }

}