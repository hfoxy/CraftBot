package me.hfox.craftbot.connection.client;

import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.connection.client.session.Session;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.exception.connection.BotConnectionException;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.terminal.CommandSender;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public interface Client extends CommandSender {

    default void connect(String host) throws BotConnectionException {
        connect(host, null);
    }

    default void connect(String host, int port) throws BotConnectionException {
        connect(host, port, null);
    };

    default void connect(String host, ConnectAction action) throws BotConnectionException {
        connect(host, 25565, action);
    }

    void connect(String host, int port, ConnectAction action) throws BotConnectionException;

    Connection getConnection();

    Session getSession();

    UUID getUniqueId();

    String getName();

    Map<UUID, PlayerInfo> getKnownPlayers();

    boolean isChatEnabled();

    void setChatEnabled(boolean chatEnabled);

    default void completeLogin() {
        // nothing
    }

    default void onSend(ClientPacket packet) {
        // no implementation required
    }

    default void onReceive(ServerPacket packet) {
        // no implementation required
    }

    void execute(String command);

}
