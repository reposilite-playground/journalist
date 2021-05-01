/*
 * Copyright (c) 2021 dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dzikoysk.dynamiclogger;

import org.panda_lang.utilities.commons.collection.Lists;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * Channel represents level of logging.
 * List of predefined channels with their priorities:
 *
 * <ul>
 *     <li>{@link Channel#FATAL} - 60.0</li>
 *     <li>{@link Channel#ERROR} - 50.0</li>
 *     <li>{@link Channel#WARN} - 40.0</li>
 *     <li>{@link Channel#INFO} - 30.0</li>
 *     <li>{@link Channel#DEBUG} - 20.0</li>
 *     <li>{@link Channel#TRACE} - 10.0</li>
 * </ul>
 *
 * The higher priority is, the the more important type of information it represents.
 */
public class Channel implements Comparable<Channel> {

    private static final Set<Channel> PREDEFINED_CHANNELS = new TreeSet<>();

    public static final Channel FATAL = Lists.add(PREDEFINED_CHANNELS, new Channel("fatal", 60.0, ChannelIntention.NEGATIVE));
    public static final Channel ERROR = Lists.add(PREDEFINED_CHANNELS, new Channel("error", 50.0, ChannelIntention.NEGATIVE));
    public static final Channel WARN = Lists.add(PREDEFINED_CHANNELS, new Channel("warn", 40.0, ChannelIntention.NEGATIVE));
    public static final Channel INFO = Lists.add(PREDEFINED_CHANNELS, new Channel("info", 30.0, ChannelIntention.POSITIVE));
    public static final Channel DEBUG = Lists.add(PREDEFINED_CHANNELS, new Channel("debug", 20.0, ChannelIntention.POSITIVE));
    public static final Channel TRACE = Lists.add(PREDEFINED_CHANNELS, new Channel("trace", 10.0, ChannelIntention.POSITIVE));

    private final String name;
    private final double priority;
    private final ChannelIntention intention;

    public Channel(String name, double priority, ChannelIntention intention) {
        this.name = name;
        this.priority = priority;
        this.intention = intention;
    }

    @Override
    public int compareTo(Channel to) {
        return Double.compare(priority, to.priority);
    }

    /**
     * Get the estimated intention of channel
     *
     * @return channel intention
     */
    public ChannelIntention getIntention() {
        return intention;
    }

    /**
     * Get the priority of channel
     *
     * @return channel priority
     */
    public double getPriority() {
        return priority;
    }

    /**
     * Get the name of channel
     *
     * @return channel name
     */
    public String getName() {
        return name;
    }

    /**
     * Find channel using its string representation.
     * This method supports only predefined channels.
     *
     * @param channel the channel to look for
     * @return the lookup result
     */
    public static Optional<Channel> of(String channel) {
        return PREDEFINED_CHANNELS.stream()
                .filter(value -> value.getName().equalsIgnoreCase(channel))
                .findAny();
    }

    /**
     * Get predefined channels as set
     *
     * @return a copy of predfined channels set
     */
    public static Set<Channel> getPredefinedChannels() {
        return new TreeSet<>(PREDEFINED_CHANNELS);
    }

}
