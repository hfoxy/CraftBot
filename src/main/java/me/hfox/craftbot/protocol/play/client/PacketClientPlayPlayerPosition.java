package me.hfox.craftbot.protocol.play.client;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketClientPlayPlayerPosition implements ClientPacket {

    private double x;
    private double y;
    private double z;
    private boolean grounded;

    public PacketClientPlayPlayerPosition(double x, double y, double z, boolean grounded) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.grounded = grounded;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean isGrounded() {
        return grounded;
    }

    @Override
    public void write(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        buffer.writeBoolean(grounded);
    }

}
