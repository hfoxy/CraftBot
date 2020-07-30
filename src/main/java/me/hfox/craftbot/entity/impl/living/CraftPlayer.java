package me.hfox.craftbot.entity.impl.living;

import me.hfox.craftbot.chat.ChatColour;
import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.ActiveHand;
import me.hfox.craftbot.entity.data.DisplayedSkinParts;
import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.entity.translator.EntityIndexTranslatorBase;
import me.hfox.craftbot.entity.translator.HierarchyEntityIndexTranslatorBase;
import me.hfox.craftbot.nbt.Tag;
import me.hfox.craftbot.player.Gamemode;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.play.server.data.player.PlayerInfoProperty;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.utils.ToStringBuilder;
import me.hfox.craftbot.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftPlayer extends CraftLivingEntity implements Player {

    private static final Logger LOGGER = LoggerFactory.getLogger(CraftPlayer.class);

    private final PlayerInfo info;

    private float additionalHearts = 0;
    private int score = 0;
    private DisplayedSkinParts skinParts = new DisplayedSkinParts(
            false, false, false, false, false, false, false
    );

    private Hand mainHand = Hand.RIGHT;
    private Tag leftShoulderEntity = null;
    private Tag rightShoulderEntity = null;

    public CraftPlayer(World world, int id, UUID uuid, EntityType<? extends Player, ?> entityType, PlayerInfo info) {
        super(world, id, uuid, entityType);
        this.info = info;
    }

    @Override
    public String getName() {
        return info.getName();
    }

    public UUID getUuid() {
        return info.getUuid();
    }

    @Override
    public PlayerInfoProperty[] getProperties() {
        return info.getProperties();
    }

    @Override
    public Gamemode getGameMode() {
        return info.getGamemode();
    }

    @Override
    public int getPing() {
        return info.getPing();
    }

    @Override
    public Optional<ChatComponent> getDisplayName() {
        return info.getDisplayName();
    }

    @Override
    public boolean isDisplayNameSet() {
        return info.isDisplayNameSet();
    }

    @Override
    public Optional<ChatComponent> getCustomName() {
        return getDisplayName();
    }

    @Override
    public boolean isCustomNameVisible() {
        return isDisplayNameSet();
    }

    @Override
    public float getAdditionalHearts() {
        return additionalHearts;
    }

    @Override
    public void setAdditionalHearts(float additionalHearts) {
        this.additionalHearts = additionalHearts;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public DisplayedSkinParts getSkinParts() {
        return skinParts;
    }

    @Override
    public void setSkinParts(DisplayedSkinParts skinParts) {
        this.skinParts = skinParts;
    }

    @Override
    public Hand getMainHand() {
        return mainHand;
    }

    @Override
    public void setMainHand(Hand mainHand) {
        this.mainHand = mainHand;
    }

    @Override
    public Tag getLeftShoulderEntity() {
        return leftShoulderEntity;
    }

    @Override
    public void setLeftShoulderEntity(Tag leftShoulderEntity) {
        this.leftShoulderEntity = leftShoulderEntity;
    }

    @Override
    public Tag getRightShoulderEntity() {
        return rightShoulderEntity;
    }

    @Override
    public void setRightShoulderEntity(Tag rightShoulderEntity) {
        this.rightShoulderEntity = rightShoulderEntity;
    }

    @Override
    public String getBriefInfo() {
        return getClass().getSimpleName() + "[name='" + getName() + "',customName='"
                + getCustomName() + ChatColour.RESET + "',location=" + getLocation() + "]";
    }

    @Override
    public String toString() {
        return ToStringBuilder.build(this).deepArray(true).parents(true).reflective(true).simpleName(true).toString();
    }

    public static class Translator extends HierarchyEntityIndexTranslatorBase<Player> {

        public Translator() {
            super(Player.class, new CraftLivingEntity.Translator());
        }

        @Override
        public void read(Player entity, EntityMetadata metadata) throws IOException {
            int index = metadata.getIndex();
            ProtocolBuffer buffer = metadata.getBufferValue();

            if (index == 14) {
                entity.setAdditionalHearts(buffer.readFloat());
            } else if (index == 15) {
                entity.setScore(buffer.readVarInt());
            } else if (index == 16) {
                byte flags = buffer.readByte();

                boolean cape = hasFlag(flags, 0x01);
                boolean jacket = hasFlag(flags, 0x02);
                boolean leftSleeve = hasFlag(flags, 0x04);
                boolean rightSleeve = hasFlag(flags, 0x08);
                boolean leftTrouser = hasFlag(flags, 0x10);
                boolean rightTrouser = hasFlag(flags, 0x20);
                boolean hat = hasFlag(flags, 0x40);

                DisplayedSkinParts skinParts = new DisplayedSkinParts(
                        cape, jacket, leftSleeve, rightSleeve, leftTrouser, rightTrouser, hat
                );

                entity.setSkinParts(skinParts);
            } else if (index == 17) {
                entity.setMainHand(buffer.readVarInt() == 0 ? Hand.LEFT : Hand.RIGHT);
            } else if (index == 18) {
                entity.setLeftShoulderEntity(buffer.readTag());
            } else if (index == 19) {
                entity.setRightShoulderEntity(buffer.readTag());
            }
        }

    }

}
