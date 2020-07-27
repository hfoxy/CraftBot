package me.hfox.craftbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.chat.ChatComponentDeserializer;
import me.hfox.craftbot.chat.StringSupportedChatComponent;

public class CraftBotImpl implements CraftBot {

    private final String name;
    private final String version;
    private final ObjectMapper mapper;

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

        this.mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(StringSupportedChatComponent.class, new ChatComponentDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setBot(CraftBot bot) {
        if (Bot.BOT != null) {
            throw new IllegalStateException("Bot already set");
        }

        Bot.BOT = bot;
    }

}
