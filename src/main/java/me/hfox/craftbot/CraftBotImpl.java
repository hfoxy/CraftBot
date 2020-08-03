package me.hfox.craftbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Lists;
import me.hfox.craftbot.chat.ChatComponentDeserializer;
import me.hfox.craftbot.chat.StringSupportedChatComponent;
import me.hfox.craftbot.connection.client.Client;

import java.util.*;

public class CraftBotImpl implements CraftBot {

    private final String name;
    private final String version;
    private final Set<Integer> supportedProtocols;
    private final ObjectMapper mapper;

    private final Map<UUID, Client> uuidClients;
    private final Map<String, Client> nameClients;

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
        this.supportedProtocols = new HashSet<>(Lists.newArrayList(578));
        setBot(this);

        this.mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(StringSupportedChatComponent.class, new ChatComponentDeserializer());
        mapper.registerModule(module);

        this.uuidClients = new HashMap<>();
        this.nameClients = new HashMap<>();
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
    public Set<Integer> getSupportedProtocols() {
        return supportedProtocols;
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
