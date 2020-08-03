package me.hfox.craftbot.connection.client.session;

import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;

import java.util.UUID;

public class OfflineSession implements UpdatableSession {

    private final String name;
    private UUID uniqueId;

    public OfflineSession(String name) {
        this.name = name;
        this.uniqueId = UUID.randomUUID();
    }

    @Override
    public boolean isOnline() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public void authenticate() {
        // no auth required
    }

    @Override
    public void joinServer(String serverHash) {
        // nothing required here
    }

}
