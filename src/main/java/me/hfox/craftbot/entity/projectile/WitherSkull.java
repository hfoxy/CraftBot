package me.hfox.craftbot.entity.projectile;

import me.hfox.craftbot.entity.Entity;

public interface WitherSkull extends Entity {

    boolean isInvulnerable();

    void setInvulnerable(boolean invulnerable);

}
