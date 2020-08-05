package me.hfox.craftbot.protocol.play.client;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.world.CommandBlockMode;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class PacketClientPlayUpdateCommandBlock implements ClientPacket {

    private Location location;
    private String command;
    private CommandBlockMode mode;
    private boolean trackOutput;
    private boolean conditional;
    private boolean automatic;

    public PacketClientPlayUpdateCommandBlock(Location location, String command, CommandBlockMode mode, boolean trackOutput, boolean conditional, boolean automatic) {
        this.location = location;
        this.command = command;
        this.mode = mode;
        this.trackOutput = trackOutput;
        this.conditional = conditional;
        this.automatic = automatic;
    }

    public Location getLocation() {
        return location;
    }

    public String getCommand() {
        return command;
    }

    public CommandBlockMode getMode() {
        return mode;
    }

    public boolean isTrackOutput() {
        return trackOutput;
    }

    public boolean isConditional() {
        return conditional;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    @Override
    public void write(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        // TODO: this I did it for the wrong side
        /*location = buffer.readPosition();
        command = buffer.readString();
        mode = CommandBlockMode.values()[buffer.readVarInt()];

        byte flags = buffer.readByte();
        trackOutput = hasFlag(flags, 0x01);
        conditional = hasFlag(flags, 0x02);
        automatic = hasFlag(flags, 0x04);*/
    }

}
