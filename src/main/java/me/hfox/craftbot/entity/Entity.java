package me.hfox.craftbot.entity;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.entity.data.Pose;

import java.util.Optional;
import java.util.UUID;

public interface Entity {

    int getId();

    UUID getUniqueId();

    EntityType getEntityType();

    BoundingBox getBoundingBox();

    boolean isOnFire();

    void setOnFire(boolean onFire);

    boolean isCrouched();

    void setCrouched(boolean crouched);

    @Deprecated
    boolean isRiding();

    void setRiding(boolean riding);

    boolean isSprinting();

    void setSprinting(boolean sprinting);

    boolean isSwimming();

    void setSwimming(boolean swimming);

    boolean isInvisible();

    void setInvisible(boolean invisible);

    boolean isGlowing();

    void setGlowing(boolean glowing);

    boolean isElytraFlying();

    void setElytraFlying(boolean elytraFlying);

    int getAir();

    void setAir(int air);

    Optional<ChatComponent> getCustomName();

    void setCustomName(ChatComponent customName);

    boolean isCustomNameVisible();

    void setCustomNameVisible(boolean customNameVisible);

    boolean isSilent();

    void setSilent(boolean silent);

    boolean hasGravity();

    void setGravity(boolean gravity);

    Pose getPose();

    void setPose(Pose pose);

}
