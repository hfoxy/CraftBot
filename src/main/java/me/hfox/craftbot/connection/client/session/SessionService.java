package me.hfox.craftbot.connection.client.session;

public final class SessionService {

    public static Session authenticated(String username, String password) {
        return new YggdrasilSession(username, password);
    }

    public static Session offline(String name) {
        return new OfflineSession(name);
    }

}
