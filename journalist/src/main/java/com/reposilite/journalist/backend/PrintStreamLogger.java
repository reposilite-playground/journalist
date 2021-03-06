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

package com.reposilite.journalist.backend;

import com.reposilite.journalist.Channel;
import com.reposilite.journalist.ChannelIntention;

import java.io.PrintStream;

/**
 * Uses {@link java.lang.System#in} and {@link java.lang.System#err} to log messages
 */
public class PrintStreamLogger extends DefaultLogger {

    private final PrintStream out;
    private final PrintStream err;
    private final boolean newline;

    public PrintStreamLogger(PrintStream out, PrintStream err, Channel threshold, boolean newline) {
        super(threshold);
        this.out = out;
        this.err = err;
        this.newline = newline;
    }

    /**
     * Uses {@link com.reposilite.journalist.Channel#INFO} as default threshold.
     */
    public PrintStreamLogger(PrintStream out, PrintStream err) {
        this(out, err, Channel.ALL, true);
    }

    @Override
    protected void internalLog(Channel channel, String message) {
        if (channel.getIntention() == ChannelIntention.NEGATIVE) {
            if (newline) {
                err.println(message);
            }
            else {
                err.print(message);
            }
        }
        else {
            if (newline) {
                out.println(message);
            }
            else {
                out.print(message);
            }
        }
    }

}
