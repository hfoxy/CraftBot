package me.hfox.craftbot.entity;

import me.hfox.craftbot.protocol.play.server.data.SlotData;

public interface Fireball extends Entity {

    SlotData getItem();

    void setItem(SlotData item);

}
