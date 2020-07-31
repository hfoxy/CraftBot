package me.hfox.craftbot.entity.living.mob.monster;

import me.hfox.craftbot.entity.data.VillagerData;

public interface ZombieVillager extends Zombie {

    boolean isConverting();

    void setConverting(boolean converting);

    VillagerData getVillagerData();

    void setVillagerData(VillagerData villagerData);

}
