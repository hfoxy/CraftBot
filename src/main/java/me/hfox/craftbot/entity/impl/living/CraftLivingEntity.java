package me.hfox.craftbot.entity.impl.living;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.ActiveHand;
import me.hfox.craftbot.entity.data.Pose;
import me.hfox.craftbot.entity.impl.CraftEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.translator.EntityIndexTranslatorBase;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftLivingEntity extends CraftEntity implements LivingEntity {

    private boolean handActive = false;
    private ActiveHand activeHand = ActiveHand.MAIN_HAND;
    private boolean inRiptideSpinAttack = false;
    private float health = 1;
    private int potionEffectColour = 0;
    private boolean potionEffectAmbient = false;
    private int stuckArrowCount = 0;
    private int absorptionHealth = 0;
    private Location bedLocation = null;

    public CraftLivingEntity(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isHandActive() {
        return handActive;
    }

    @Override
    public void setHandActive(boolean handActive) {
        this.handActive = handActive;
    }

    @Override
    public ActiveHand getActiveHand() {
        return activeHand;
    }

    @Override
    public void setActiveHand(ActiveHand activeHand) {
        this.activeHand = activeHand;
    }

    @Override
    public boolean isInRiptideSpinAttack() {
        return inRiptideSpinAttack;
    }

    @Override
    public void setInRiptideSpinAttack(boolean inRiptideSpinAttack) {
        this.inRiptideSpinAttack = inRiptideSpinAttack;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    @Override
    public int getPotionEffectColour() {
        return potionEffectColour;
    }

    @Override
    public void setPotionEffectColour(int potionEffectColour) {
        this.potionEffectColour = potionEffectColour;
    }

    @Override
    public boolean isPotionEffectAmbient() {
        return potionEffectAmbient;
    }

    @Override
    public void setPotionEffectAmbient(boolean potionEffectAmbient) {
        this.potionEffectAmbient = potionEffectAmbient;
    }

    @Override
    public int getStuckArrowCount() {
        return stuckArrowCount;
    }

    @Override
    public void setStuckArrowCount(int stuckArrowCount) {
        this.stuckArrowCount = stuckArrowCount;
    }

    @Override
    public int getAbsorptionHealth() {
        return absorptionHealth;
    }

    @Override
    public void setAbsorptionHealth(int absorptionHealth) {
        this.absorptionHealth = absorptionHealth;
    }

    @Override
    public Optional<Location> getBedLocation() {
        Location result = bedLocation;
        if (result != null) {
            result = result.copy();
        }

        return Optional.ofNullable(result);
    }

    @Override
    public void setBedLocation(Location bedLocation) {
        this.bedLocation = bedLocation;
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<LivingEntity> {

        public Translator() {
            super(LivingEntity.class, new CraftEntity.Translator());
        }

        @Override
        public void read(LivingEntity entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 7) {
                byte flags = buffer.readByte();
                entity.setHandActive(hasFlag(flags, 0x01));
                entity.setActiveHand(hasFlag(flags, 0x02) ? ActiveHand.MAIN_HAND : ActiveHand.OFFHAND);
                entity.setInRiptideSpinAttack(hasFlag(flags, 0x04));
            } else if (index == 8) {
                entity.setHealth(buffer.readFloat());
            } else if (index == 9) {
                entity.setPotionEffectColour(buffer.readVarInt());
            } else if (index == 10) {
                entity.setPotionEffectAmbient(buffer.readBoolean());
            } else if (index == 11) {
                entity.setStuckArrowCount(buffer.readVarInt());
            } else if (index == 12) {
                entity.setAbsorptionHealth(buffer.readVarInt());
            } else if (index == 13) {
                entity.setBedLocation(buffer.readPositionOptional().orElse(null));
            }
        }

    }

}
