package me.hfox.craftbot.entity.mob.monster;

import me.hfox.craftbot.entity.data.SpellcasterIllagerSpell;

public interface SpellcasterIllager extends Illager {

    SpellcasterIllagerSpell getSpell();

    void setSpell(SpellcasterIllagerSpell spell);

}
