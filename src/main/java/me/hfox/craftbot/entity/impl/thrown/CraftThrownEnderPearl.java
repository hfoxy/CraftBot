package me.hfox.craftbot.entity.impl.thrown;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.CraftEntity;
import me.hfox.craftbot.entity.thrown.ThrownEgg;
import me.hfox.craftbot.entity.thrown.ThrownEnderPearl;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class CraftThrownEnderPearl extends CraftEntity implements ThrownEnderPearl {

    private SlotData item;

    public CraftThrownEnderPearl(World world, int id, UUID uuid, EntityType<? extends Entity, ?> entityType) {
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

    public static class Translator extends HierarchyEntityIndexTranslatorBase<ThrownEnderPearl> {

        public Translator() {
            super(ThrownEnderPearl.class, new CraftEntity.Translator());
        }

        @Override
        public void read(ThrownEnderPearl entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 7) {
                entity.setItem(buffer.readSlot());
            }
        }

    }

}
