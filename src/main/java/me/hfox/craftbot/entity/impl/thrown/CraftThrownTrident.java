package me.hfox.craftbot.entity.impl.thrown;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.CraftEntity;
import me.hfox.craftbot.entity.thrown.ThrownTrident;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftThrownTrident extends CraftEntity implements ThrownTrident {

    private boolean critical = false;
    private boolean noClip = false;
    private byte piercingLevel;
    private byte loyaltyLevel;
    private boolean enchanted;

    public CraftThrownTrident(World world, int id, UUID uuid, EntityType<? extends Entity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isCritical() {
        return critical;
    }

    @Override
    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    @Override
    public boolean isNoClip() {
        return noClip;
    }

    @Override
    public void setNoClip(boolean noClip) {
        this.noClip = noClip;
    }

    @Override
    public byte getPiercingLevel() {
        return piercingLevel;
    }

    @Override
    public void setPiercingLevel(byte piercingLevel) {
        this.piercingLevel = piercingLevel;
    }

    @Override
    public byte getLoyaltyLevel() {
        return loyaltyLevel;
    }

    @Override
    public void setLoyaltyLevel(byte loyaltyLevel) {
        this.loyaltyLevel = loyaltyLevel;
    }

    @Override
    public boolean isEnchanted() {
        return enchanted;
    }

    @Override
    public void setEnchanted(boolean enchanted) {
        this.enchanted = enchanted;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<ThrownTrident> {

        public Translator() {
            super(ThrownTrident.class, new CraftEntity.Translator());
        }

        @Override
        public void read(ThrownTrident entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 7) {
                byte flags = buffer.readByte();
                entity.setCritical(hasFlag(flags, 0x01));
                entity.setNoClip(hasFlag(flags, 0x02));
            } else if (index == 8) {
                entity.setPiercingLevel(buffer.readByte());
            } else if (index == 9) {
                entity.setLoyaltyLevel(buffer.readByte());
            } else if (index == 10) {
                entity.setEnchanted(buffer.readBoolean());
            }
        }

    }

}
