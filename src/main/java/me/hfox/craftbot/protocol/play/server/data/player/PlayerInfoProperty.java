package me.hfox.craftbot.protocol.play.server.data.player;

import java.util.Optional;

public class PlayerInfoProperty {

    private final String name;
    private final String value;
    private final String signature;

    public PlayerInfoProperty(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Optional<String> getSignature() {
        return Optional.ofNullable(signature);
    }

}
