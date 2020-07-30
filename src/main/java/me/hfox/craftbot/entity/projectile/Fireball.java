package me.hfox.craftbot.entity.projectile;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.protocol.play.server.data.SlotData;

import java.util.Optional;

public interface Fireball extends Entity {

    Optional<SlotData> getItem();

    void setItem(SlotData item);

}
