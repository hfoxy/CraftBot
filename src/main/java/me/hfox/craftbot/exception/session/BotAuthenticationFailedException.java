package me.hfox.craftbot.exception.session;

public class BotAuthenticationFailedException extends BotSessionException {

    public BotAuthenticationFailedException() {
    }

    public BotAuthenticationFailedException(String message) {
        super(message);
    }

    public BotAuthenticationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
