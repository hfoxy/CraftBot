package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.SpellcasterIllagerSpell;
import me.hfox.craftbot.entity.mob.Illager;

public interface SpellcasterIllager extends Illager {

    SpellcasterIllagerSpell getSpell();

    void setSpell(SpellcasterIllagerSpell spell);

}
