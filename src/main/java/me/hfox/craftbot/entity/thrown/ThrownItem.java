package me.hfox.craftbot.entity.thrown;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.protocol.play.server.data.SlotData;

import java.util.Optional;

public interface ThrownItem extends Entity {

    Optional<SlotData> getItem();

    void setItem(SlotData item);

}
