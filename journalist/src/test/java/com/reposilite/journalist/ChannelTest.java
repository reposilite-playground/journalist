package com.reposilite.journalist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChannelTest {

    @Test
    void shouldHaveValidEqualsAndHashCodeImplementations() {
        Channel channel = new Channel("all", 0, ChannelIntention.NEUTRAL);
        assertNotEquals(null, channel);

        Channel same = new Channel("all", 0, ChannelIntention.NEUTRAL);
        assertTrue(channel.equals(same) && same.equals(channel));
        assertEquals(channel.hashCode(), same.hashCode());

        Channel withDifferentName = new Channel("one", -1, ChannelIntention.NEUTRAL);
        Channel withDifferentPriority = new Channel("all", -1, ChannelIntention.NEUTRAL);
        Channel withDifferentIntention = new Channel("all", -1, ChannelIntention.NEGATIVE);

        for (Channel variant : new Channel[] { withDifferentName, withDifferentPriority, withDifferentIntention }) {
            assertFalse(channel.equals(variant) || variant.equals(channel));
            assertNotEquals(channel.hashCode(), variant.hashCode());
        }
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