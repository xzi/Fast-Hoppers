package com.banana.fasthoppers;

import org.slf4j.LoggerFactory;

public class Logger {
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("Fast Hoppers");

    public static void log(String message) {
        LOGGER.info(message);
    }

    public static void info(String message) {
        log(message);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    
    public static org.slf4j.Logger getLogger() {
        return LOGGER;
    }
}
