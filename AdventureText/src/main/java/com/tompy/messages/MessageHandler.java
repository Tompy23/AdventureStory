package com.tompy.messages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class MessageHandler {
    private static final Logger LOGGER = LogManager.getLogger(MessageHandler.class);

    private Properties properties = new Properties();

    public MessageHandler() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("i18n.properties"));
        } catch (IOException ioe) {
            LOGGER.error("Failed to load text.");
            System.exit(5);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

}
