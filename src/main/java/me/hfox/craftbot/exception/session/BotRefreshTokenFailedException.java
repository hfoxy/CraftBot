package me.hfox.craftbot.exception.session;

public class BotRefreshTokenFailedException extends BotSessionException {

    public BotRefreshTokenFailedException() {
    }

    public BotRefreshTokenFailedException(String message) {
        super(message);
    }

    public BotRefreshTokenFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
