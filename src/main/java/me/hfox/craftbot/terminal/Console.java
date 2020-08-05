package me.hfox.craftbot.terminal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Console implements CommandSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Console.class);

    private final String prefix;

    public Console() {
        this(null);
    }

    public Console(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void sendMessage(Level level, String message, Object... args) {
        String out = prefix != null ? "[" + prefix + "] " + message : message;

        switch (level) {
            case DEBUG:
                LOGGER.debug(out, args);
                break;
            case TRACE:
                LOGGER.trace(out, args);
                break;
            case INFO:
                LOGGER.info(out, args);
                break;
            case WARN:
                LOGGER.warn(out, args);
                break;
            case ERROR:
                LOGGER.error(out, args);
                break;
        }
    }

    @Override
    public void sendMessage(String message, Object... args) {
        sendMessage(Level.INFO, message, args);
    }

    @Override
    public void sendException(Level level, String message, Throwable throwable) {
        String out = prefix != null ? "[" + prefix + "] " + message : message;

        switch (level) {
            case DEBUG:
                LOGGER.debug(out, throwable);
                break;
            case TRACE:
                LOGGER.trace(out, throwable);
                break;
            case INFO:
                LOGGER.info(out, throwable);
                break;
            case WARN:
                LOGGER.warn(out, throwable);
                break;
            case ERROR:
                LOGGER.error(out, throwable);
                break;
        }
    }

    @Override
    public void sendException(String message, Throwable throwable) {
        sendException(Level.ERROR, message, throwable);
    }

}
