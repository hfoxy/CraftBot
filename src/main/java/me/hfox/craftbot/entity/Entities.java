package me.hfox.craftbot.entity;

import me.hfox.craftbot.entity.data.creation.ClientPlayerEntityType;
import me.hfox.craftbot.entity.data.creation.EntityCreationData;
import me.hfox.craftbot.entity.data.creation.PlayerCreationData;
import me.hfox.craftbot.entity.data.creation.PlayerEntityType;
import me.hfox.craftbot.entity.impl.CraftItemEntity;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.impl.living.mob.CraftAgeableMob;
import me.hfox.craftbot.entity.impl.living.mob.CraftEnderDragon;
import me.hfox.craftbot.entity.impl.living.mob.CraftEnderman;
import me.hfox.craftbot.entity.impl.living.mob.CraftSlime;
import me.hfox.craftbot.entity.impl.living.mob.ambient.CraftBat;
import me.hfox.craftbot.entity.impl.living.mob.animal.CraftChicken;
import me.hfox.craftbot.entity.impl.living.mob.animal.CraftCow;
import me.hfox.craftbot.entity.impl.living.mob.animal.CraftPig;
import me.hfox.craftbot.entity.impl.living.mob.animal.CraftSheep;
import me.hfox.craftbot.entity.impl.living.mob.flying.CraftPhantom;
import me.hfox.craftbot.entity.impl.living.mob.monster.*;
import me.hfox.craftbot.entity.impl.projectile.CraftArrow;
import me.hfox.craftbot.entity.impl.thrown.*;
import me.hfox.craftbot.entity.living.ClientPlayer;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.entity.living.mob.EnderDragon;
import me.hfox.craftbot.entity.living.mob.Slime;
import me.hfox.craftbot.entity.living.mob.ambient.Bat;
import me.hfox.craftbot.entity.living.mob.animal.Chicken;
import me.hfox.craftbot.entity.living.mob.animal.Cow;
import me.hfox.craftbot.entity.living.mob.animal.Pig;
import me.hfox.craftbot.entity.living.mob.animal.Sheep;
import me.hfox.craftbot.entity.living.mob.flying.Phantom;
import me.hfox.craftbot.entity.living.mob.monster.*;
import me.hfox.craftbot.entity.projectile.Arrow;
import me.hfox.craftbot.entity.thrown.*;

public class Entities {

    public static final EntityType<Arrow, ?> ARROW = new BaseEntityType<>(CraftArrow.class, 2, "minecraft:arrow");
    public static final EntityType<Bat, ?> BAT = new BaseEntityType<>(CraftBat.class, 3, "minecraft:bat");
    public static final EntityType<Chicken, ?> CHICKEN = new BaseEntityType<>(CraftChicken.class, 9, "minecraft:chicken");
    public static final EntityType<Cow, ?> COW = new BaseEntityType<>(CraftCow.class, 11, "minecraft:cow");
    public static final EntityType<Creeper, ?> CREEPER = new BaseEntityType<>(CraftCreeper.class, 12, "minecraft:creeper");
    public static final EntityType<EnderDragon, ?> ENDER_DRAGON = new BaseEntityType<>(CraftEnderDragon.class, 19, "minecraft:ender_dragon");
    public static final EntityType<Enderman, ?> ENDERMAN = new BaseEntityType<>(CraftEnderman.class, 20, "minecraft:enderman");
    public static final EntityType<Skeleton, ?> SKELETON = new BaseEntityType<>(CraftSkeleton.class, 66, "minecraft:skeleton");
    public static final EntityType<EyeOfEnder, ?> EYE_OF_ENDER = new BaseEntityType<>(CraftEyeOfEnder.class, 25, "minecraft:eye_of_ender");
    public static final EntityType<ItemEntity, ?> ITEM = new BaseEntityType<>(CraftItemEntity.class, 35, "minecraft:item");
    public static final EntityType<Pig, ?> PIG = new BaseEntityType<>(CraftPig.class, 55, "minecraft:pig");
    public static final EntityType<Sheep, ?> SHEEP = new BaseEntityType<>(CraftSheep.class, 62, "minecraft:sheep");
    public static final EntityType<Slime, ?> SLIME = new BaseEntityType<>(CraftSlime.class, 68, "minecraft:slime");
    public static final EntityType<Snowball, ?> SNOWBALL = new BaseEntityType<>(CraftSnowball.class, 71, "minecraft:snowball");
    public static final EntityType<Spider, ?> SPIDER = new BaseEntityType<>(CraftSpider.class, 73, "minecraft:spider");
    public static final EntityType<ThrownEgg, ?> THROWN_EGG = new BaseEntityType<>(CraftThrownEgg.class, 79, "minecraft:thrown_egg");
    public static final EntityType<ThrownEnderPearl, ?> THROWN_ENDER_PEARL = new BaseEntityType<>(CraftThrownEnderPearl.class, 80, "minecraft:thrown_ender_pearl");
    public static final EntityType<ThrownExperienceBottle, ?> THROWN_EXPERIENCE_BOTTLE = new BaseEntityType<>(CraftThrownExperienceBottle.class, 81, "minecraft:experience_bottle");
    public static final EntityType<ThrownPotion, ?> THROWN_POTION = new BaseEntityType<>(CraftThrownPotion.class, 82, "minecraft:potion");
    public static final EntityType<ThrownTrident, ?> TRIDENT = new BaseEntityType<>(CraftThrownTrident.class, 83, "minecraft:trident");
    public static final EntityType<Witch, ?> WITCH = new BaseEntityType<>(CraftWitch.class, 90, "minecraft:witch");
    public static final EntityType<Zombie, ?> ZOMBIE = new BaseEntityType<>(CraftZombie.class, 95, "minecraft:zombie");
    public static final EntityType<ZombieVillager, ?> ZOMBIE_VILLAGER = new BaseEntityType<>(CraftZombieVillager.class, 97, "minecraft:zombie_villager");
    public static final EntityType<Phantom, ?> PHANTOM = new BaseEntityType<>(CraftPhantom.class, 98, "minecraft:phantom");
    public static final EntityType<Player, PlayerCreationData> PLAYER = new PlayerEntityType(106, "minecraft:player");
    public static final EntityType<ClientPlayer, PlayerCreationData> CLIENT_PLAYER = new ClientPlayerEntityType(999, "minecraft:client_player");

    public static void load() {
        // actually does nothing, but it means the fields are instantiated
    }

}
