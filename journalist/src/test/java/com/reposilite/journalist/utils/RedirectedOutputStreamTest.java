package com.reposilite.journalist.utils;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class RedirectedOutputStreamTest {

    @Test
    void shouldProperlyDistributeContent() {
        AtomicReference<String> message = new AtomicReference<>();
        RedirectedOutputStream redirectedOutputStream = new RedirectedOutputStream(message::set);
        redirectedOutputStream.toPrintStream().println("message1");
        assertEquals("message1", message.get().trim());
    }

}