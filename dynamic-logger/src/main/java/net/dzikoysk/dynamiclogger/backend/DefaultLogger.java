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

package net.dzikoysk.dynamiclogger.backend;

import net.dzikoysk.dynamiclogger.Channel;
import net.dzikoysk.dynamiclogger.Logger;
import net.dzikoysk.dynamiclogger.utils.RedirectedOutputStream;

import java.io.PrintStream;

public abstract class DefaultLogger implements Logger {

    protected Channel threshold;

    public DefaultLogger(Channel threshold) {
        this.threshold = threshold;
    }

    protected abstract void internalLog(Channel channel, String message);

    @Override
    public Logger log(Channel channel, String message) {
        if (channel.getPriority() >= threshold.getPriority()) {
            internalLog(channel, message);
        }

        return this;
    }

    @Override
    public Logger error(String message) {
        log(Channel.ERROR, "# " + message.replace("\n", "\n# "));
        return this;
    }

    @Override
    public Logger exception(Channel channel, Throwable throwable) {
        PrintStream printStream = toPrintStream(channel);
        throwable.printStackTrace(printStream);
        printStream.close();
        return this;
    }

    @Override
    public Logger setThreshold(Channel threshold) {
        this.threshold = threshold;
        return this;
    }

    @Override
    public PrintStream toPrintStream(Channel channel) {
        return new RedirectedOutputStream(message -> log(channel, message)).toPrintStream();
    }

}
