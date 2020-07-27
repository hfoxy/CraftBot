package me.hfox.craftbot.connection.client;

import me.hfox.craftbot.protocol.handshake.client.PacketClientHandshake;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.login.client.PacketClientLoginStart;

public class PlayClient extends BasicClient {

    @Override
    protected void onConnect(String host, int port) {
        getConnection().writePacket(new PacketClientHandshake(578, host, port, ProtocolState.LOGIN));
        getConnection().writePacket(new PacketClientLoginStart("PenguBot"));
    }

}
