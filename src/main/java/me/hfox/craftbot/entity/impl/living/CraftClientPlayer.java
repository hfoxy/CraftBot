package me.hfox.craftbot.entity.impl.living;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.entity.living.ClientPlayer;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayEntityAction;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityAction;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftClientPlayer extends CraftPlayer implements ClientPlayer {

    public CraftClientPlayer(World world, int id, UUID uuid, EntityType<? extends ClientPlayer, ?> entityType, PlayerInfo info) {
        super(world, id, uuid, entityType, info);
    }

    @Override
    public void setSprinting(boolean sprinting) {
        boolean changed = sprinting != isSprinting();
        super.setSprinting(sprinting);

        if (changed) {
            // changed
            EntityAction action = sprinting ? EntityAction.START_SPRINTING : EntityAction.STOP_SPRINTING;
            getWorld().getClient().getConnection().writePacket(new PacketClientPlayEntityAction(getId(), action, 0));
        }
    }

}
