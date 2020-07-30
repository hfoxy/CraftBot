package me.hfox.craftbot.entity.impl.living.mob;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.DragonPhase;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.EnderDragon;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftEnderDragon extends CraftMob implements EnderDragon {

    private DragonPhase dragonPhase;

    public CraftEnderDragon(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public DragonPhase getDragonPhase() {
        return dragonPhase;
    }

    @Override
    public void setDragonPhase(DragonPhase dragonPhase) {
        this.dragonPhase = dragonPhase;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 15) {
            setDragonPhase(DragonPhase.values()[buffer.readVarInt()]);
        }
    }

}
