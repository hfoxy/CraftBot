package me.hfox.craftbot.connection.client;

import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.exception.connection.BotConnectionException;

public interface Client {

    default void connect(String host) throws BotConnectionException {
        connect(host, 25565);
    }

    void connect(String host, int port) throws BotConnectionException;

    Connection getConnection();

}
