package com.reposilite.journalist.tinylog;

import com.reposilite.journalist.slf4j.Slf4jChannel;
import org.tinylog.Level;

public final class TinyLogLevel {

    public static Slf4jChannel toSlf4jChannel(Level level) {
        switch (level) {
            case TRACE: return Slf4jChannel.TRACE;
            case DEBUG: return Slf4jChannel.DEBUG;
            case INFO: return Slf4jChannel.INFO;
            case WARN: return Slf4jChannel.WARN;
            case ERROR: return Slf4jChannel.ERROR;
            default: return Slf4jChannel.INFO;
        }
    }

}
