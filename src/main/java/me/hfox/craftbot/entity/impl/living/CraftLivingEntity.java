package me.hfox.craftbot.entity.impl.living;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.ActiveHand;
import me.hfox.craftbot.entity.impl.CraftEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.World;

import java.util.Optional;
import java.util.UUID;

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

}
