package me.hfox.craftbot.connection.client.session;

import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;

import java.util.UUID;

public interface Session {

    boolean isOnline();

    String getName();

    UUID getUniqueId();

    void authenticate() throws BotAuthenticationFailedException;

    void joinServer(String serverHash) throws BotAuthenticationFailedException;

}
