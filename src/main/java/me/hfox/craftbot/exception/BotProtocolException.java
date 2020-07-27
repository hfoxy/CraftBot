package me.hfox.craftbot.exception;

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
