package me.hfox.craftbot.entity.impl.living.mob.monster;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.SpellcasterIllagerSpell;
import me.hfox.craftbot.entity.living.mob.monster.SpellcasterIllager;
import me.hfox.craftbot.entity.living.mob.monster.Vindicator;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftSpellcasterIllager extends CraftIllagerBase implements SpellcasterIllager {

    private SpellcasterIllagerSpell spell;

    public CraftSpellcasterIllager(World world, int id, UUID uuid, EntityType<? extends Vindicator, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public SpellcasterIllagerSpell getSpell() {
        return spell;
    }

    @Override
    public void setSpell(SpellcasterIllagerSpell spell) {
        this.spell = spell;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 16) {
            setSpell(SpellcasterIllagerSpell.values()[buffer.readByte()]);
        }
    }

}
