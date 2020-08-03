package me.hfox.craftbot.connection.client.session;

import java.util.UUID;

public interface UpdatableSession extends Session {

    void setUniqueId(UUID uniqueId);

}
