package me.hfox.craftbot.entity.impl.living.mob.monster;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.monster.Creeper;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftCreeper extends CraftMonster implements Creeper {

    private boolean fused;
    private boolean charged;
    private boolean ignited;

    public CraftCreeper(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isFused() {
        return fused;
    }

    @Override
    public void setFused(boolean fused) {
        this.fused = fused;
    }

    @Override
    public boolean isCharged() {
        return charged;
    }

    @Override
    public void setCharged(boolean charged) {
        this.charged = charged;
    }

    @Override
    public boolean isIgnited() {
        return ignited;
    }

    @Override
    public void setIgnited(boolean ignited) {
        this.ignited = ignited;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<Creeper> {

        public Translator() {
            super(Creeper.class, new CraftLivingEntity.Translator());
        }

        @Override
        public void read(Creeper entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 15) {
                entity.setFused(buffer.readVarInt() == 1);
            } else if (index == 16) {
                entity.setCharged(buffer.readBoolean());
            } else if (index == 17) {
                entity.setIgnited(buffer.readBoolean());
            }
        }

    }

}
