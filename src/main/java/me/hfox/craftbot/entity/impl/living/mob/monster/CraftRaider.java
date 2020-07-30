package me.hfox.craftbot.entity.impl.living.mob.monster;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.monster.Raider;
import me.hfox.craftbot.entity.living.mob.monster.Zombie;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftRaider extends CraftMonster implements Raider {

    private boolean celebrating;

    public CraftRaider(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isCelebrating() {
        return celebrating;
    }

    @Override
    public void setCelebrating(boolean celebrating) {
        this.celebrating = celebrating;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<Raider> {

        public Translator() {
            super(Raider.class, new CraftLivingEntity.Translator());
        }

        @Override
        public void read(Raider entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 15) {
                entity.setCelebrating(buffer.readBoolean());
            }
        }

    }

}
