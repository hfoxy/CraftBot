package me.hfox.craftbot.connection;

import me.hfox.craftbot.connection.compression.Compression;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.Protocol;
import me.hfox.craftbot.protocol.ServerPacket;

public interface Connection {

    Protocol getProtocol();

    Compression getCompression();

    boolean isConnected();

    void disconnect();

    void writePacket(ClientPacket packet);

    void handle(ServerPacket packet);

    void handle(ClientPacket packet);

}
