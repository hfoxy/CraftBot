package me.hfox.craftbot.entity.impl.living.mob;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.AgeableMob;
import me.hfox.craftbot.entity.living.mob.monster.Zombie;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftAgeableMob extends CraftPathfinderMob implements AgeableMob {

    private boolean baby;

    public CraftAgeableMob(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isBaby() {
        return baby;
    }

    @Override
    public void setBaby(boolean baby) {
        this.baby = baby;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<AgeableMob> {

        public Translator() {
            super(AgeableMob.class, new CraftLivingEntity.Translator());
        }

        @Override
        public void read(AgeableMob entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 15) {
                entity.setBaby(buffer.readBoolean());
            }
        }

    }

}
