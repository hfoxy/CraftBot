package me.hfox.craftbot.entity.particle;

import me.hfox.craftbot.exception.entity.BotUnknownParticleTypeException;

public enum ParticleType {

    AMBIENT_ENTITY_EFFECT(0, "minecraft:ambient_entity_effect"),
    ANGRY_VILLAGER(1, "minecraft:angry_villager"),
    BARRIER(2, "minecraft:barrier"),
    BLOCK(3, "minecraft:block", true),
    BUBBLE(4, "minecraft:bubble"),
    CLOUD(5, "minecraft:cloud"),
    CRIT(6, "minecraft:crit"),
    DAMAGE_INDICATOR(7, "minecraft:damage_indicator"),
    DRAGON_BREATH(8, "minecraft:dragon_breath"),
    DRIPPING_LAVA(9, "minecraft:dripping_lava"),
    FALLING_LAVA(10, "minecraft:falling_lava"),
    LANDING_LAVA(11, "minecraft:landing_lava"),
    DRIPPING_WATER(12, "minecraft:dripping_water"),
    FALLING_WATER(13, "minecraft:falling_water"),
    DUST(14, "minecraft:dust", true),
    EFFECT(15, "minecraft:effect"),
    ELDER_GUARDIAN(16, "minecraft:elder_guardian"),
    ENCHANTED_HIT(17, "minecraft:enchanted_hit"),
    ENCHANT(18, "minecraft:enchant"),
    END_ROD(19, "minecraft:end_rod"),
    ENTITY_EFFECT(20, "minecraft:entity_effect"),
    EXPLOSION_EMITTER(21, "minecraft:explosion_emitter"),
    EXPLOSION(22, "minecraft:explosion"),
    FALLING_DUST(23, "minecraft:falling_dust", true),
    FIREWORK(24, "minecraft:firework"),
    FISHING(25, "minecraft:fishing"),
    FLAME(26, "minecraft:flame"),
    FLASH(27, "minecraft:flash"),
    HAPPY_VILLAGER(28, "minecraft:happy_villager"),
    COMPOSTER(29, "minecraft:composter"),
    HEART(30, "minecraft:heart"),
    INSTANT_EFFECT(31, "minecraft:instant_effect"),
    ITEM(32, "minecraft:item", true),
    ITEM_SLIME(33, "minecraft:item_slime"),
    ITEM_SNOWBALL(34, "minecraft:item_snowball"),
    LARGE_SMOKE(35, "minecraft:large_smoke"),
    LAVA(36, "minecraft:lava"),
    MYCELIUM(37, "minecraft:mycelium"),
    NOTE(38, "minecraft:note"),
    POOF(39, "minecraft:poof"),
    PORTAL(40, "minecraft:portal"),
    RAIN(41, "minecraft:rain"),
    SMOKE(42, "minecraft:smoke"),
    SNEEZE(43, "minecraft:sneeze"),
    SPIT(44, "minecraft:spit"),
    SQUID_INK(45, "minecraft:squid_ink"),
    SWEEP_ATTACK(46, "minecraft:sweep_attack"),
    TOTEM_OF_UNDYING(47, "minecraft:totem_of_undying"),
    UNDERWATER(48, "minecraft:underwater"),
    SPLASH(49, "minecraft:splash"),
    WITCH(50, "minecraft:witch"),
    BUBBLE_POP(51, "minecraft:bubble_pop"),
    CURRENT_DOWN(52, "minecraft:current_down"),
    BUBBLE_COLUMN_UP(53, "minecraft:bubble_column_up"),
    NAUTILUS(54, "minecraft:nautilus"),
    DOLPHIN(55, "minecraft:dolphin"),
    CAMPFIRE_COSY_SMOKE(56, "minecraft:campfire_cosy_smoke"),
    CAMPFIRE_SIGNAL_SMOKE(57, "minecraft:campfire_signal_smoke"),
    DRIPPING_HONEY(58, "minecraft:dripping_honey"),
    FALLING_HONEY(59, "minecraft:falling_honey"),
    LANDING_HONEY(60, "minecraft:landing_honey"),
    FALLING_NECTAR(61, "minecraft:falling_nectar");

    private final int id;
    private final String name;
    private final boolean data;

    ParticleType(int id, String name) {
        this(id, name, false);
    }

    ParticleType(int id, String name, boolean data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean hasData() {
        return data;
    }

    public static ParticleType findById(int id) {
        for (ParticleType particleType : values()) {
            if (particleType.getId() == id) {
                return particleType;
            }
        }

        throw new BotUnknownParticleTypeException(Integer.toString(id));
    }

}
