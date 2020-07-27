package me.hfox.craftbot.exception;

public class BotException extends Exception {

    public BotException() {
        //
    }

    public BotException(String message) {
        super(message);
    }

    public BotException(String message, Throwable cause) {
        super(message, cause);
    }

}
