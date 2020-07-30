package me.hfox.craftbot.connection.client;

import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.exception.connection.BotConnectionException;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.ServerPacket;

import java.util.Map;
import java.util.UUID;

public interface Client {

    default void connect(String host) throws BotConnectionException {
        connect(host, 25565);
    }

    void connect(String host, int port) throws BotConnectionException;

    Connection getConnection();

    UUID getUniqueId();

    String getName();

    Map<UUID, PlayerInfo> getKnownPlayers();

    default void completeLogin() {
        // nothing
    }

    default void onSend(ClientPacket packet) {
        // no implementation required
    }

    default void onReceive(ServerPacket packet) {
        // no implementation required
    }

}
