package me.hfox.craftbot.terminal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Console implements CommandSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Console.class);

    @Override
    public void sendMessage(Level level, String message, Object... args) {
        switch (level) {
            case DEBUG:
                LOGGER.debug(message, args);
                break;
            case TRACE:
                LOGGER.trace(message, args);
                break;
            case INFO:
                LOGGER.info(message, args);
                break;
            case WARN:
                LOGGER.warn(message, args);
                break;
            case ERROR:
                LOGGER.error(message, args);
                break;
        }
    }

    @Override
    public void sendMessage(String message, Object... args) {
        sendMessage(Level.INFO, message, args);
    }

    @Override
    public void sendException(Level level, String message, Throwable throwable) {
        switch (level) {
            case DEBUG:
                LOGGER.debug(message, throwable);
                break;
            case TRACE:
                LOGGER.trace(message, throwable);
                break;
            case INFO:
                LOGGER.info(message, throwable);
                break;
            case WARN:
                LOGGER.warn(message, throwable);
                break;
            case ERROR:
                LOGGER.error(message, throwable);
                break;
        }
    }

    @Override
    public void sendException(String message, Throwable throwable) {
        sendException(Level.ERROR, message, throwable);
    }

}
