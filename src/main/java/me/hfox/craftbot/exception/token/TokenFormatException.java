package me.hfox.craftbot.exception.token;

import me.hfox.aphelion.exception.CommandUsageException;

public class TokenFormatException extends CommandUsageException {

    public TokenFormatException() {
    }

    public TokenFormatException(String message) {
        super(message);
    }

}
