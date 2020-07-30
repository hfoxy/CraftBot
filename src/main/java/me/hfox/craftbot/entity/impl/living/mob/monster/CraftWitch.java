package me.hfox.craftbot.entity.impl.living.mob.monster;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.monster.Raider;
import me.hfox.craftbot.entity.living.mob.monster.Witch;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftWitch extends CraftRaider implements Witch {

    private boolean drinkingPotion;

    public CraftWitch(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isDrinkingPotion() {
        return drinkingPotion;
    }

    @Override
    public void setDrinkingPotion(boolean drinkingPotion) {
        this.drinkingPotion = drinkingPotion;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<Witch> {

        public Translator() {
            super(Witch.class, new CraftRaider.Translator());
        }

        @Override
        public void read(Witch entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 16) {
                entity.setDrinkingPotion(buffer.readBoolean());
            }
        }

    }

}
