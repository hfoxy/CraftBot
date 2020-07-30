package me.hfox.craftbot.entity.living;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.data.ActiveHand;
import me.hfox.craftbot.world.Location;

import java.util.Optional;

public interface LivingEntity extends Entity {

    boolean isHandActive();

    void setHandActive(boolean handActive);

    ActiveHand getActiveHand();

    void setActiveHand(ActiveHand activeHand);

    boolean isInRiptideSpinAttack();

    void setInRiptideSpinAttack(boolean inRiptideSpinAttack);

    float getHealth();

    void setHealth(float health);

    int getPotionEffectColour();

    void setPotionEffectColour(int potionEffectColour);

    boolean isPotionEffectAmbient();

    void setPotionEffectAmbient(boolean potionEffectAmbient);

    int getStuckArrowCount();

    void setStuckArrowCount(int stuckArrowCount);

    int getAbsorptionHealth();

    void setAbsorptionHealth(int absorptionHealth);

    Optional<Location> getBedLocation();

    void setBedLocation(Location location);

}
