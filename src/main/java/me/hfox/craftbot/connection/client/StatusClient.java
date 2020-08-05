package me.hfox.craftbot.connection.client;

import me.hfox.craftbot.connection.client.session.SessionService;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import me.hfox.craftbot.protocol.handshake.client.PacketClientHandshake;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.status.client.PacketClientStatusRequest;

public class StatusClient extends BasicClient<StatusClient> {

    public StatusClient() throws BotAuthenticationFailedException {
        super(StatusClient.class, SessionService.offline("ping"));
    }

    @Override
    protected void onConnect(String host, int port) {
        getConnection().writePacket(new PacketClientHandshake(578, host, port, ProtocolState.STATUS));
        getConnection().writePacket(new PacketClientStatusRequest());
    }

}
