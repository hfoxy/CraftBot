package me.hfox.craftbot.exception;

public class BotRuntimeException extends RuntimeException {

    public BotRuntimeException() {
        //
    }

    public BotRuntimeException(String message) {
        super(message);
    }

    public BotRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
