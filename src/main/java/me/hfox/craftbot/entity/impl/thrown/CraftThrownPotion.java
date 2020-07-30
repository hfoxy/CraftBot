package me.hfox.craftbot.entity.impl.thrown;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.CraftEntity;
import me.hfox.craftbot.entity.thrown.ThrownEgg;
import me.hfox.craftbot.entity.thrown.ThrownPotion;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class CraftThrownPotion extends CraftEntity implements ThrownPotion {

    private SlotData item;

    public CraftThrownPotion(World world, int id, UUID uuid, EntityType<? extends Entity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public Optional<SlotData> getItem() {
        return Optional.ofNullable(item);
    }

    @Override
    public void setItem(SlotData item) {
        this.item = item;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<ThrownPotion> {

        public Translator() {
            super(ThrownPotion.class, new CraftEntity.Translator());
        }

        @Override
        public void read(ThrownPotion entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 7) {
                entity.setItem(buffer.readSlot());
            }
        }

    }

}
