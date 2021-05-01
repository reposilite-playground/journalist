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

import java.io.PrintStream;

/**
 * Proxy interface for dynamic logging handler
 */
public interface Logger {

    /**
     * Log the given message to {@link net.dzikoysk.dynamiclogger.Channel#FATAL} channel
     *
     * @param message the message to log
     * @return the logger instance
     */
    default Logger fatal(String message) {
        return log(Channel.FATAL, message);
    }

    /**
     * Log the given message to {@link net.dzikoysk.dynamiclogger.Channel#ERROR} channel
     *
     * @param message the message to log
     * @return the logger instance
     */
    default Logger error(String message) {
        return log(Channel.ERROR, message);
    }

    /**
     * Log the given message to {@link net.dzikoysk.dynamiclogger.Channel#WARN}
     *
     * @param message the message to log
     * @return the logger instance
     */
    default Logger warn(String message) {
        return log(Channel.WARN, message);
    }

    /**
     * Log the given message to {@link net.dzikoysk.dynamiclogger.Channel#INFO}
     *
     * @param message the message to log
     * @return the logger instance
     */
    default Logger info(String message) {
        return log(Channel.INFO, message);
    }

    /**
     * Log the given message to {@link net.dzikoysk.dynamiclogger.Channel#DEBUG}
     *
     * @param message the message to log
     * @return the logger instance
     */
    default Logger debug(String message) {
        return log(Channel.DEBUG, message);
    }

    /**
     * Log the given message to {@link net.dzikoysk.dynamiclogger.Channel#TRACE}
     *
     * @param message the message to log
     * @return the logger instance
     */
    default Logger trace(String message) {
        return log(Channel.TRACE, message);
    }

    /**
     * Log the given message to the provided channel
     *
     * @param channel the channel to log to
     * @param message the message to log
     * @return the logger instance
     */
    Logger log(Channel channel, String message);

    /**
     * Log the given exception to the {@link net.dzikoysk.dynamiclogger.Channel#ERROR} channel
     *
     * @param throwable the exception to log
     * @return the logger instance
     */
    default Logger exception(Throwable throwable) {
        return exception(Channel.ERROR, throwable);
    }

    /**
     * Log the given exception to the given channel
     *
     * @param channel the channel to log to
     * @param throwable the exception to log
     * @return the logger instance
     */
    Logger exception(Channel channel, Throwable throwable);

    /**
     * Modify current threshold
     *
     * @param threshold the threshold to set
     * @return the logger instance
     */
    Logger setThreshold(Channel threshold);

    /**
     * Create PrintStream that will write to the current logger.
     * Always remember about closing it after usage.
     *
     * @return the associated print stream
     */
    PrintStream toPrintStream(Channel channel);

}
