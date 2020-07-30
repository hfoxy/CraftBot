package me.hfox.craftbot.entity.impl.living.mob;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.monster.Enderman;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;
import me.hfox.craftbot.world.palette.BlockStateDto;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class CraftEnderman extends CraftMob implements Enderman {

    private BlockStateDto carriedBlock;
    private boolean screaming;
    private boolean staredAt;

    public CraftEnderman(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public Optional<BlockStateDto> getCarriedBlock() {
        return Optional.ofNullable(carriedBlock);
    }

    @Override
    public void setCarriedBlock(BlockStateDto carriedBlock) {
        this.carriedBlock = carriedBlock;
    }

    @Override
    public boolean isScreaming() {
        return screaming;
    }

    @Override
    public void setScreaming(boolean screaming) {
        this.screaming = screaming;
    }

    @Override
    public boolean isStaredAt() {
        return staredAt;
    }

    @Override
    public void setStaredAt(boolean staredAt) {
        this.staredAt = staredAt;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 15) {
            setCarriedBlock(buffer.readBlockStateOptional().orElse(null));
        } else if (index == 16) {
            setScreaming(buffer.readBoolean());
        } else if (index == 17) {
            setStaredAt(buffer.readBoolean());
        }
    }

}
