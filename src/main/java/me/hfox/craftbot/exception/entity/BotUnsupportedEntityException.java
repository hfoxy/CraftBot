package me.hfox.craftbot.exception.entity;

public class BotUnsupportedEntityException extends RuntimeException {

    public BotUnsupportedEntityException(String message) {
        super(message);
    }

    public BotUnsupportedEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
