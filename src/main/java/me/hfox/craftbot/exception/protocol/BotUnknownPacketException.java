package me.hfox.craftbot.exception.protocol;

public class BotUnknownPacketException extends BotProtocolException {

    public BotUnknownPacketException() {
        //
    }

    public BotUnknownPacketException(String message) {
        super(message);
    }

    public BotUnknownPacketException(String message, Throwable cause) {
        super(message, cause);
    }

}
