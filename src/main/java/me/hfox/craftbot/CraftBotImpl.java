package me.hfox.craftbot;

public class CraftBotImpl implements CraftBot {

    private final String name;
    private final String version;

    public CraftBotImpl() {
        String name = CraftBot.class.getPackage().getImplementationTitle();
        if (name == null) {
            name = "craftbot";
        }

        this.name = name;

        String version = CraftBot.class.getPackage().getImplementationVersion();
        if (version == null) {
            version = "unknown";
        }

        this.version = version;
        setBot(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setBot(CraftBot bot) {
        if (Bot.BOT != null) {
            throw new IllegalStateException("Bot already set");
        }

        Bot.BOT = bot;
    }

}
