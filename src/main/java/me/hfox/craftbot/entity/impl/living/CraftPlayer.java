package me.hfox.craftbot.entity.impl.living;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.DisplayedSkinParts;
import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.nbt.Tag;
import me.hfox.craftbot.player.Gamemode;
import me.hfox.craftbot.protocol.play.server.data.player.PlayerInfoProperty;
import me.hfox.craftbot.world.World;

import java.util.Optional;
import java.util.UUID;

public class CraftPlayer extends CraftLivingEntity implements Player {

    private final PlayerInfo info;

    private float additionalHearts = 0;
    private int score = 0;
    private DisplayedSkinParts skinParts = new DisplayedSkinParts(false, false, false, false, false, false, false);
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

}
