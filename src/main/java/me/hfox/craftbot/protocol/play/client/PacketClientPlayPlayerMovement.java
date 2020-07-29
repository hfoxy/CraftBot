package me.hfox.craftbot.protocol.play.client;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketClientPlayPlayerMovement implements ClientPacket {

    private boolean grounded;

    public PacketClientPlayPlayerMovement(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isGrounded() {
        return grounded;
    }

    @Override
    public void write(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        buffer.writeBoolean(grounded);
    }

}
