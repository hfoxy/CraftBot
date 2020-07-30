package me.hfox.craftbot.entity.impl.living.mob;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.Slime;
import me.hfox.craftbot.entity.living.mob.monster.Zombie;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftSlime extends CraftMob implements Slime {

    private int size;

    public CraftSlime(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<Slime> {

        public Translator() {
            super(Slime.class, new CraftLivingEntity.Translator());
        }

        @Override
        public void read(Slime entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 15) {
                entity.setSize(buffer.readVarInt());
            }
        }

    }

}
