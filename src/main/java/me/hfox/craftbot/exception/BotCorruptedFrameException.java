package me.hfox.craftbot.exception;

public class BotCorruptedFrameException extends BotProtocolException {

    public BotCorruptedFrameException() {
        //
    }

    public BotCorruptedFrameException(String message) {
        super(message);
    }

    public BotCorruptedFrameException(String message, Throwable cause) {
        super(message, cause);
    }

}
