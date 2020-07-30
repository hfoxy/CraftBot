package me.hfox.craftbot.entity.impl.living.mob;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.Slime;
import me.hfox.craftbot.entity.living.mob.monster.Enderman;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class CraftEnderman extends CraftMob implements Enderman {

    private Integer carriedBlock;
    private boolean screaming;
    private boolean staredAt;

    public CraftEnderman(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public Optional<Integer> getCarriedBlock() {
        return Optional.ofNullable(carriedBlock);
    }

    @Override
    public void setCarriedBlock(Integer carriedBlock) {
        this.carriedBlock = carriedBlock;
    }

    @Override
    public boolean isScreaming() {
        return screaming;
    }

    @Override
    public void setScreaming(boolean screaming) {
        this.screaming = screaming;
    }

    @Override
    public boolean isStaredAt() {
        return staredAt;
    }

    @Override
    public void setStaredAt(boolean staredAt) {
        this.staredAt = staredAt;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<Enderman> {

        public Translator() {
            super(Enderman.class, new CraftLivingEntity.Translator());
        }

        @Override
        public void read(Enderman entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 15) {
                entity.setCarriedBlock(buffer.readVarInt());
            } else if (index == 16) {
                entity.setScreaming(buffer.readBoolean());
            } else if (index == 17) {
                entity.setStaredAt(buffer.readBoolean());
            }
        }

    }

}
