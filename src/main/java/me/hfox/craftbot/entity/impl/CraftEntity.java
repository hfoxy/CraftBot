package me.hfox.craftbot.entity.impl;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.entity.BoundingBox;
import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.Pose;
import me.hfox.craftbot.entity.translator.EntityIndexTranslatorBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftEntity implements Entity {

    private final int id;
    private final UUID uuid;
    private final EntityType entityType;

    private boolean onFire;
    private boolean crouched;
    private boolean riding;
    private boolean sprinting;
    private boolean swimming;
    private boolean invisible;
    private boolean glowing;
    private boolean elytraFlying;
    private int air;
    private ChatComponent customName;
    private boolean customNameVisible;
    private boolean silent;
    private boolean gravity;
    private Pose pose;

    public CraftEntity(int id, UUID uuid, EntityType entityType) {
        this.id = id;
        this.uuid = uuid;
        this.entityType = entityType;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(0, 0, 0);
    }

    @Override
    public boolean isOnFire() {
        return onFire;
    }

    @Override
    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    @Override
    public boolean isCrouched() {
        return crouched;
    }

    @Override
    public void setCrouched(boolean crouched) {
        this.crouched = crouched;
    }

    @Override
    public boolean isRiding() {
        return riding;
    }

    @Override
    public void setRiding(boolean riding) {
        this.riding = riding;
    }

    @Override
    public boolean isSprinting() {
        return sprinting;
    }

    @Override
    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }

    @Override
    public boolean isSwimming() {
        return swimming;
    }

    @Override
    public void setSwimming(boolean swimming) {
        this.swimming = swimming;
    }

    @Override
    public boolean isInvisible() {
        return invisible;
    }

    @Override
    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    @Override
    public boolean isGlowing() {
        return glowing;
    }

    @Override
    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    @Override
    public boolean isElytraFlying() {
        return elytraFlying;
    }

    @Override
    public void setElytraFlying(boolean elytraFlying) {
        this.elytraFlying = elytraFlying;
    }

    @Override
    public int getAir() {
        return air;
    }

    @Override
    public void setAir(int air) {
        this.air = air;
    }

    @Override
    public Optional<ChatComponent> getCustomName() {
        return Optional.ofNullable(customName);
    }

    @Override
    public void setCustomName(ChatComponent customName) {
        this.customName = customName;
    }

    @Override
    public boolean isCustomNameVisible() {
        return customNameVisible;
    }

    @Override
    public void setCustomNameVisible(boolean customNameVisible) {
        this.customNameVisible = customNameVisible;
    }

    @Override
    public boolean isSilent() {
        return silent;
    }

    @Override
    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    @Override
    public boolean hasGravity() {
        return gravity;
    }

    @Override
    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    @Override
    public Pose getPose() {
        return pose;
    }

    @Override
    public void setPose(Pose pose) {
        this.pose = pose;
    }

    public static class Translator extends EntityIndexTranslatorBase<Entity> {

        public Translator() {
            super(Entity.class);
        }

        @Override
        public void read(Entity entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 0) {
                byte flags = buffer.readByte();
                entity.setOnFire(hasFlag(flags, 0x01));
                entity.setCrouched(hasFlag(flags, 0x02));
                entity.setRiding(hasFlag(flags, 0x04));
                entity.setSprinting(hasFlag(flags, 0x08));
                entity.setSwimming(hasFlag(flags, 0x10));
                entity.setInvisible(hasFlag(flags, 0x20));
                entity.setGlowing(hasFlag(flags, 0x40));
                entity.setElytraFlying(hasFlag(flags, 0x80));
            } else if (index == 1) {
                entity.setAir(buffer.readVarInt());
            } else if (index == 2) {
                entity.setCustomName(buffer.readChat());
            } else if (index == 3) {
                entity.setCustomNameVisible(buffer.readBoolean());
            } else if (index == 4) {
                entity.setSilent(buffer.readBoolean());
            } else if (index == 5) {
                entity.setGravity(!buffer.readBoolean());
            } else if (index == 6) {
                entity.setPose(Pose.values()[buffer.readVarInt()]);
            }
        }

    }

}
