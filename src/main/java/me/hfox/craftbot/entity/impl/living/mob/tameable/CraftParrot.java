package me.hfox.craftbot.entity.impl.living.mob.tameable;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.ParrotType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.animal.tameable.Parrot;
import me.hfox.craftbot.entity.living.mob.animal.tameable.Wolf;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftParrot extends CraftTameable implements Parrot {

    private ParrotType type;

    public CraftParrot(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public ParrotType getType() {
        return type;
    }

    @Override
    public void setType(ParrotType type) {
        this.type = type;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 18) {
            setType(ParrotType.values()[buffer.readVarInt()]);
        }
    }

}
