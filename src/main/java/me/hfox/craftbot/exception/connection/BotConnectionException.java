package me.hfox.craftbot.exception.connection;

import me.hfox.craftbot.exception.BotException;

public class BotConnectionException extends BotException {

    public BotConnectionException(String message) {
        super(message);
    }

    public BotConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
