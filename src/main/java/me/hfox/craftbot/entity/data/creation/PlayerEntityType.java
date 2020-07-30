package me.hfox.craftbot.entity.data.creation;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftPlayer;
import me.hfox.craftbot.entity.living.Player;

public class PlayerEntityType extends EntityType<Player, PlayerCreationData> {

    public PlayerEntityType(int typeId, String identifier) {
        super(CraftPlayer.class, typeId, identifier, PlayerCreationData.class);
    }

    @Override
    protected CraftPlayer createEntityMappedData(PlayerCreationData data) {
        return new CraftPlayer(data.getWorld(), data.getEntityId(), data.getUuid(), this, data.getInfo());
    }

}
