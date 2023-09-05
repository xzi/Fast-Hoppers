package com.banana.fasthoppers;


public class Logger {
    // generate log4j logger
    public static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager
            .getLogger("Fast Hoppers");

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

}
