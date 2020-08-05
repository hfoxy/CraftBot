package me.hfox.craftbot;

import me.hfox.craftbot.connection.client.session.dto.SessionServerConfiguration;

public class Bot {

    static SessionServerConfiguration SESSION_CONFIGURATION;
    static CraftBot BOT;

    public static SessionServerConfiguration getSessionConfiguration() {
        return SESSION_CONFIGURATION;
    }

    public static CraftBot getBot() {
        return BOT;
    }

}
