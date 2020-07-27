package me.hfox.craftbot.exception;

public class BotPacketDecodingException extends BotProtocolException {

    public BotPacketDecodingException() {
        //
    }

    public BotPacketDecodingException(String message) {
        super(message);
    }

    public BotPacketDecodingException(String message, Throwable cause) {
        super(message, cause);
    }

}
