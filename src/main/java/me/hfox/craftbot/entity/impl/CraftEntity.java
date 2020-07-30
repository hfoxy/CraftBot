package me.hfox.craftbot.entity.impl;

import me.hfox.craftbot.chat.ChatColour;
import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.entity.BoundingBox;
import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.Pose;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.Velocity;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftEntity implements Entity {

    private final World world;

    private final int id;
    private final UUID uuid;
    private final EntityType entityType;

    private boolean onFire = false;
    private boolean crouched = false;
    private boolean riding = false;
    private boolean sprinting = false;
    private boolean swimming = false;
    private boolean invisible = false;
    private boolean glowing = false;
    private boolean elytraFlying = false;
    private int air = 300;
    private ChatComponent customName;
    private boolean customNameVisible = false;
    private boolean silent = false;
    private boolean gravity = true;
    private Pose pose = Pose.STANDING;

    private Location location;
    private Velocity velocity;

    public CraftEntity(World world, int id, UUID uuid, EntityType<? extends Entity, ?> entityType) {
        this.world = world;
        this.id = id;
        this.uuid = uuid;
        this.entityType = entityType;
    }

    @Override
    public World getWorld() {
        return world;
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

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public Velocity getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    @Override
    public String getBriefInfo() {
        return getClass().getSimpleName() + "[customName='"
                + getCustomName() + ChatColour.RESET + "',location=" + location + "]";
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 0) {
            byte flags = buffer.readByte();
            setOnFire(hasFlag(flags, 0x01));
            setCrouched(hasFlag(flags, 0x02));
            setRiding(hasFlag(flags, 0x04));
            setSprinting(hasFlag(flags, 0x08));
            setSwimming(hasFlag(flags, 0x10));
            setInvisible(hasFlag(flags, 0x20));
            setGlowing(hasFlag(flags, 0x40));
            setElytraFlying(hasFlag(flags, 0x80));
        } else if (index == 1) {
            setAir(buffer.readVarInt());
        } else if (index == 2) {
            setCustomName(buffer.readChatOptional().orElse(null));
        } else if (index == 3) {
            setCustomNameVisible(buffer.readBoolean());
        } else if (index == 4) {
            setSilent(buffer.readBoolean());
        } else if (index == 5) {
            setGravity(!buffer.readBoolean());
        } else if (index == 6) {
            setPose(Pose.values()[buffer.readVarInt()]);
        }
    }

}
