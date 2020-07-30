package me.hfox.craftbot.entity.impl.living.mob;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.Mob;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftMob extends CraftLivingEntity implements Mob {

    private boolean ai;
    private Hand mainHand;

    public CraftMob(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean hasAi() {
        return ai;
    }

    @Override
    public void setAi(boolean ai) {
        this.ai = ai;
    }

    @Override
    public Hand getMainHand() {
        return mainHand;
    }

    @Override
    public void setMainHand(Hand mainHand) {
        this.mainHand = mainHand;
    }

}
