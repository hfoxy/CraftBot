package me.hfox.craftbot.entity.impl.living.mob.animal;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.AgeableMob;
import me.hfox.craftbot.entity.living.mob.animal.Chicken;
import me.hfox.craftbot.entity.living.mob.animal.Sheep;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftSheep extends CraftAnimal implements Sheep {

    private int colour;
    private boolean sheared;

    public CraftSheep(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public int getColour() {
        return colour;
    }

    @Override
    public void setColour(int colour) {
        this.colour = colour;
    }

    @Override
    public boolean isSheared() {
        return sheared;
    }

    @Override
    public void setSheared(boolean sheared) {
        this.sheared = sheared;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<Sheep> {

        public Translator() {
            super(Sheep.class, new CraftLivingEntity.Translator());
        }

        @Override
        public void read(Sheep entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 16) {
                byte flags = buffer.readByte();
                entity.setColour(flags & 0x0F);
                entity.setSheared(hasFlag(flags, 0x10));
            }
        }

    }

}
