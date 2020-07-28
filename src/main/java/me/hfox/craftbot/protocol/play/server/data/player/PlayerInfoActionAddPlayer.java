package me.hfox.craftbot.protocol.play.server.data.player;

public interface PlayerInfoActionAddPlayer extends PlayerInfoAction, PlayerInfoActionDisplayName, PlayerInfoActionGamemode, PlayerInfoActionLatency {

    String getName();

    PlayerInfoProperty[] getProperties();

}
