package me.hfox.craftbot.entity;

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

    public static final EntityType<Arrow, ?> ARROW = new EntityType<>(CraftArrow.class, 2, "Arrow", "minecraft:arrow", EntityCreationData.class, new CraftArrow.Translator());
    public static final EntityType<Bat, ?> BAT = new EntityType<>(CraftBat.class, 3, "Bat", "minecraft:bat", EntityCreationData.class, new CraftBat.Translator());
    public static final EntityType<Chicken, ?> CHICKEN = new EntityType<>(CraftChicken.class, 9, "Chicken", "minecraft:chicken", EntityCreationData.class, new CraftAgeableMob.Translator());
    public static final EntityType<Cow, ?> COW = new EntityType<>(CraftCow.class, 11, "Cow", "minecraft:cow", EntityCreationData.class, new CraftAgeableMob.Translator());
    public static final EntityType<Creeper, ?> CREEPER = new EntityType<>(CraftCreeper.class, 12, "Creeper", "minecraft:creeper", EntityCreationData.class, new CraftCreeper.Translator());
    public static final EntityType<EnderDragon, ?> ENDER_DRAGON = new EntityType<>(CraftEnderDragon.class, 19, "Ender Dragon", "minecraft:ender_dragon", EntityCreationData.class, new CraftEnderDragon.Translator());
    public static final EntityType<Enderman, ?> ENDERMAN = new EntityType<>(CraftEnderman.class, 20, "Enderman", "minecraft:enderman", EntityCreationData.class, new CraftEnderman.Translator());
    public static final EntityType<Skeleton, ?> SKELETON = new EntityType<>(CraftSkeleton.class, 66, "Skeleton", "minecraft:skeleton", EntityCreationData.class, new CraftLivingEntity.Translator());
    public static final EntityType<EyeOfEnder, ?> EYE_OF_ENDER = new EntityType<>(CraftEyeOfEnder.class, 25, "Eye of Ender", "minecraft:eye_of_ender", EntityCreationData.class, new CraftEyeOfEnder.Translator());
    public static final EntityType<ItemEntity, ?> ITEM = new EntityType<>(CraftItemEntity.class, 35, "Item", "minecraft:item", EntityCreationData.class, new CraftItemEntity.Translator());
    public static final EntityType<Pig, ?> PIG = new EntityType<>(CraftPig.class, 55, "Pig", "minecraft:pig", EntityCreationData.class, new CraftPig.Translator());
    public static final EntityType<Sheep, ?> SHEEP = new EntityType<>(CraftSheep.class, 62, "Sheep", "minecraft:sheep", EntityCreationData.class, new CraftSheep.Translator());
    public static final EntityType<Slime, ?> SLIME = new EntityType<>(CraftSlime.class, 68, "Slime", "minecraft:slime", EntityCreationData.class, new CraftSlime.Translator());
    public static final EntityType<Snowball, ?> SNOWBALL = new EntityType<>(CraftSnowball.class, 71, "Snowball", "minecraft:snowball", EntityCreationData.class, new CraftSnowball.Translator());
    public static final EntityType<Spider, ?> SPIDER = new EntityType<>(CraftSpider.class, 73, "Spider", "minecraft:spider", EntityCreationData.class, new CraftSpider.Translator());
    public static final EntityType<ThrownEgg, ?> THROWN_EGG = new EntityType<>(CraftThrownEgg.class, 79, "Thrown Egg", "minecraft:thrown_egg", EntityCreationData.class, new CraftThrownEgg.Translator());
    public static final EntityType<ThrownEnderPearl, ?> THROWN_ENDER_PEARL = new EntityType<>(CraftThrownEnderPearl.class, 80, "Thrown Ender Pearl", "minecraft:thrown_ender_pearl", EntityCreationData.class, new CraftThrownEnderPearl.Translator());
    public static final EntityType<ThrownExperienceBottle, ?> THROWN_EXPERIENCE_BOTTLE = new EntityType<>(CraftThrownExperienceBottle.class, 81, "Thrown Bottle o' Enchanting", "minecraft:experience_bottle", EntityCreationData.class, new CraftThrownExperienceBottle.Translator());
    public static final EntityType<ThrownPotion, ?> THROWN_POTION = new EntityType<>(CraftThrownPotion.class, 82, "Potion", "minecraft:potion", EntityCreationData.class, new CraftThrownPotion.Translator());
    public static final EntityType<ThrownTrident, ?> TRIDENT = new EntityType<>(CraftThrownTrident.class, 83, "Trident", "minecraft:trident", EntityCreationData.class, new CraftThrownTrident.Translator());
    public static final EntityType<Witch, ?> WITCH = new EntityType<>(CraftWitch.class, 90, "Witch", "minecraft:witch", EntityCreationData.class, new CraftWitch.Translator());
    public static final EntityType<Zombie, ?> ZOMBIE = new EntityType<>(CraftZombie.class, 95, "Zombie", "minecraft:zombie", EntityCreationData.class, new CraftZombie.Translator());
    public static final EntityType<Phantom, ?> PHANTOM = new EntityType<>(CraftPhantom.class, 98, "Phantom", "minecraft:phantom", EntityCreationData.class, new CraftPhantom.Translator());
    public static final EntityType<Player, PlayerCreationData> PLAYER = new PlayerEntityType(106, "Player", "minecraft:player");

    public static void load() {
        // actually does nothing, but it means the fields are instantiated
    }

}
