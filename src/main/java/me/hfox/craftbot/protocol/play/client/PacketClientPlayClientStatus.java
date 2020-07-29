package me.hfox.craftbot.protocol.play.client;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.play.client.data.ClientStatusAction;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketClientPlayClientStatus implements ClientPacket {

    private ClientStatusAction action;

    public PacketClientPlayClientStatus(ClientStatusAction action) {
        this.action = action;
    }

    public ClientStatusAction getAction() {
        return action;
    }

    @Override
    public void write(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        buffer.writeVarInt(action.ordinal());
    }

}
