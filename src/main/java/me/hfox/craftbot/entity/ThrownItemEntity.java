package me.hfox.craftbot.entity;

import me.hfox.craftbot.protocol.play.server.data.SlotData;

public interface ThrownItemEntity extends Entity {

    SlotData getItem();

    void setItem(SlotData item);

}
