package me.hfox.craftbot.entity.impl.living.mob.monster;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.monster.Spider;
import me.hfox.craftbot.entity.living.mob.monster.Zombie;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftSpider extends CraftMonster implements Spider {

    private boolean climbing;

    public CraftSpider(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isClimbing() {
        return climbing;
    }

    @Override
    public void setClimbing(boolean climbing) {
        this.climbing = climbing;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<Spider> {

        public Translator() {
            super(Spider.class, new CraftLivingEntity.Translator());
        }

        @Override
        public void read(Spider entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 15) {
                entity.setClimbing(buffer.readBoolean());
            }
        }

    }

}
