package me.hfox.craftbot.exception.session;

import me.hfox.craftbot.exception.BotException;

public class BotSessionException extends BotException {

    public BotSessionException() {
    }

    public BotSessionException(String message) {
        super(message);
    }

    public BotSessionException(String message, Throwable cause) {
        super(message, cause);
    }

}
