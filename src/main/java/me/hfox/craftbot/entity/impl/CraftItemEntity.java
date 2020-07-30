package me.hfox.craftbot.entity.impl;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.ItemEntity;
import me.hfox.craftbot.entity.data.DisplayedSkinParts;
import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftItemEntity extends CraftEntity implements ItemEntity {

    private SlotData item;

    public CraftItemEntity(World world, int id, UUID uuid, EntityType<? extends Entity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public SlotData getItem() {
        return item;
    }

    @Override
    public void setItem(SlotData item) {
        this.item = item;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<ItemEntity> {

        public Translator() {
            super(ItemEntity.class, new CraftEntity.Translator());
        }

        @Override
        public void read(ItemEntity entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 7) {
                entity.setItem(buffer.readSlot());
            }
        }

    }

}
