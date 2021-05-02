package net.dzikoysk.dynamiclogger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChannelTest {

    @Test
    void shouldHaveValidEqualsAndHashCodeImplementations() {
        Channel a = new Channel("all", 0, ChannelIntention.NEUTRAL);
        Channel b = new Channel("all", 0, ChannelIntention.NEUTRAL);

        assertTrue(a.equals(b) && b.equals(a));
        assertEquals(a.hashCode(), b.hashCode());

        Channel c = new Channel("all", -1, ChannelIntention.NEUTRAL);
        assertFalse(a.equals(c) || c.equals(a));
        assertNotEquals(a.hashCode(), c.hashCode());
    }

    @Test
    void shouldReturnCopyOfPredefinedChannels() {
        Channel.getPredefinedChannels().add(Channel.ALL);
        assertFalse(Channel.getPredefinedChannels().contains(Channel.ALL));
    }

    @Test
    void shouldMatchAllPredefinedChannelsByName() {
        for (Channel predefinedChannel : Channel.getPredefinedChannels()) {
            assertEquals(predefinedChannel, Channel.of(predefinedChannel.getName()).orElse(null));
        }
    }

}