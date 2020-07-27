package me.hfox.craftbot.exception.protocol;

import me.hfox.craftbot.exception.BotException;

public class BotProtocolException extends BotException {

    public BotProtocolException() {
        //
    }

    public BotProtocolException(String message) {
        super(message);
    }

    public BotProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

}
