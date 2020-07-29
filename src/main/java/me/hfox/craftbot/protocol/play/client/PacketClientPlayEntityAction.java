package me.hfox.craftbot.protocol.play.client;

import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityAction;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketClientPlayEntityAction implements ClientPacket {

    private int entityId;
    private EntityAction action;
    private int jumpBoost;

    public PacketClientPlayEntityAction(int entityId, EntityAction action, int jumpBoost) {
        this.entityId = entityId;
        this.action = action;
        this.jumpBoost = jumpBoost;
    }

    public int getEntityId() {
        return entityId;
    }

    public EntityAction getAction() {
        return action;
    }

    public int getJumpBoost() {
        return jumpBoost;
    }

    @Override
    public void write(ProtocolBuffer buffer) {
        buffer.writeVarInt(entityId);
        buffer.writeVarInt(action.ordinal());
        buffer.writeVarInt(jumpBoost);
    }

}
