package me.hfox.craftbot.entity.impl.living.mob.animal;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.animal.Pig;
import me.hfox.craftbot.entity.living.mob.animal.Sheep;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftPig extends CraftAnimal implements Pig {

    private boolean saddled;
    private int boostTime;

    public CraftPig(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isSaddled() {
        return saddled;
    }

    @Override
    public void setSaddled(boolean saddled) {
        this.saddled = saddled;
    }

    @Override
    public int getBoostTime() {
        return boostTime;
    }

    @Override
    public void setBoostTime(int boostTime) {
        this.boostTime = boostTime;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<Pig> {

        public Translator() {
            super(Pig.class, new CraftLivingEntity.Translator());
        }

        @Override
        public void read(Pig entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 16) {
                entity.setSaddled(buffer.readBoolean());
            } else if (index == 17) {
                entity.setBoostTime(buffer.readVarInt());
            }
        }

    }

}
