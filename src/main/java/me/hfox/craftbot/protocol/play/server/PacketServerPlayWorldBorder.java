package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.world.border.WorldBorderAction;
import me.hfox.craftbot.protocol.play.server.data.world.border.WorldBorderActionType;
import me.hfox.craftbot.protocol.play.server.data.world.border.impl.*;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketServerPlayWorldBorder implements ServerPacket {

    private WorldBorderActionType actionType;
    private WorldBorderAction action;

    public WorldBorderActionType getActionType() {
        return actionType;
    }

    public WorldBorderAction getAction() {
        return action;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        actionType = WorldBorderActionType.values()[buffer.readVarInt()];

        switch (actionType) {
            case SET_SIZE:
                action = new WorldBorderSetSizeImpl(buffer.readDouble());
                break;
            case LERP_SIZE:
                action = new WorldBorderLerpSizeImpl(buffer.readDouble(), buffer.readDouble(), buffer.readVarLong());
                break;
            case SET_CENTER:
                action = new WorldBorderSetCentreImpl(buffer.readDouble(), buffer.readDouble());
                break;
            case INITIALIZE:
                action = new WorldBorderInitializeImpl(
                        buffer.readDouble(),  // x
                        buffer.readDouble(),  // z
                        buffer.readDouble(),  // old diameter
                        buffer.readDouble(),  // new diameter
                        buffer.readVarLong(), // speed
                        buffer.readVarInt(),  // portal teleport boundary
                        buffer.readVarInt(),  // warning time
                        buffer.readVarInt()   // warning blocks
                );
                break;
            case SET_WARNING_TIME:
                action = new WorldBorderSetWarningTimeImpl(buffer.readVarInt());
                break;
            case SET_WARNING_BLOCKS:
                action = new WorldBorderSetWarningBlocksImpl(buffer.readVarInt());
                break;
        }
    }

}
