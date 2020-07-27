package me.hfox.craftbot.exception;

public class BotPacketRegistrationException extends BotProtocolException {

    public BotPacketRegistrationException() {
        //
    }

    public BotPacketRegistrationException(String message) {
        super(message);
    }

    public BotPacketRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

}
