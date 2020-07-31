package me.hfox.craftbot.entity.data.creation;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftClientPlayer;
import me.hfox.craftbot.entity.impl.living.CraftPlayer;
import me.hfox.craftbot.entity.living.ClientPlayer;
import me.hfox.craftbot.entity.living.Player;

public class ClientPlayerEntityType extends EntityType<ClientPlayer, PlayerCreationData> {

    public ClientPlayerEntityType(int typeId, String identifier) {
        super(CraftClientPlayer.class, typeId, identifier, PlayerCreationData.class);
    }

    @Override
    protected ClientPlayer createEntityMappedData(PlayerCreationData data) {
        return new CraftClientPlayer(data.getWorld(), data.getEntityId(), data.getUuid(), this, data.getInfo());
    }

}
