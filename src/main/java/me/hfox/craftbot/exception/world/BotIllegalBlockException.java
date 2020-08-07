package me.hfox.craftbot.exception.world;

import me.hfox.craftbot.exception.BotRuntimeException;

public class BotIllegalBlockException extends BotRuntimeException {

    public BotIllegalBlockException() {
    }

    public BotIllegalBlockException(String message) {
        super(message);
    }

    public BotIllegalBlockException(String message, Throwable cause) {
        super(message, cause);
    }

}
