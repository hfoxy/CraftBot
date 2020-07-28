package me.hfox.craftbot.entity;

import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;

public enum SpellcasterIllagerSpell {

    NONE,
    SUMMON_VEX,
    ATTACK,
    WOLOLO,
    DISAPPEAR,
    BLINDNESS;

    public static SpellcasterIllagerSpell findById(int id) {
        for (SpellcasterIllagerSpell spell : values()) {
            if (spell.ordinal() == id) {
                return spell;
            }
        }

        throw new BotUnsupportedEntityException("Unknown spellcaster illager spell type: " + id);
    }

}
