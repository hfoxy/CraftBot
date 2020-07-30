package me.hfox.craftbot.entity.impl.living.mob.ambient;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.impl.living.mob.monster.CraftMonster;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.ambient.Bat;
import me.hfox.craftbot.entity.living.mob.monster.Zombie;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftBat extends CraftAmbientCreature implements Bat {

    private boolean hanging;

    public CraftBat(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isHanging() {
        return hanging;
    }

    @Override
    public void setHanging(boolean hanging) {
        this.hanging = hanging;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<Bat> {

        public Translator() {
            super(Bat.class, new CraftLivingEntity.Translator());
        }

        @Override
        public void read(Bat entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 15) {
                entity.setHanging(buffer.readBoolean());
            }
        }

    }

}
