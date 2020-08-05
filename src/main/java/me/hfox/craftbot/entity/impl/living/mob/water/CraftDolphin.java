package me.hfox.craftbot.entity.impl.living.mob.water;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.water.Dolphin;
import me.hfox.craftbot.entity.living.mob.water.Squid;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftDolphin extends CraftWaterAnimal implements Dolphin {

    private Location treasurePosition;
    private boolean canFindTreasure;
    private boolean fish;

    public CraftDolphin(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public Location getTreasurePosition() {
        return treasurePosition;
    }

    @Override
    public void setTreasurePosition(Location treasurePosition) {
        this.treasurePosition = treasurePosition;
    }

    @Override
    public boolean canFindTreasure() {
        return canFindTreasure;
    }

    @Override
    public void setCanFindTreasure(boolean canFindTreasure) {
        this.canFindTreasure = canFindTreasure;
    }

    @Override
    public boolean hasFish() {
        return fish;
    }

    @Override
    public void setFish(boolean fish) {
        this.fish = fish;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 15) {
            setTreasurePosition(buffer.readPosition());
        } else if (index == 16) {
            setCanFindTreasure(buffer.readBoolean());
        } else if (index == 17) {
            setFish(buffer.readBoolean());
        }
    }

}
