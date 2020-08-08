package me.hfox.craftbot;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import me.hfox.craftbot.auth.AccountDto;
import me.hfox.craftbot.auth.AccountsFileDto;
import me.hfox.craftbot.auth.BotAutoAuthenticator;
import me.hfox.craftbot.chat.ChatComponentDeserializer;
import me.hfox.craftbot.chat.StringSupportedChatComponent;
import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.connection.client.PlayClient;
import me.hfox.craftbot.connection.client.session.Session;
import me.hfox.craftbot.connection.client.session.SessionService;
import me.hfox.craftbot.connection.client.session.YggdrasilSession;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CraftBotImpl implements CraftBot {

    private static final Logger LOGGER = LoggerFactory.getLogger(CraftBotImpl.class);

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("tick-handler-%d").build();
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(10, THREAD_FACTORY);

    private final String name;
    private final String version;
    private final Set<Integer> supportedProtocols;
    private final ObjectMapper mapper;

    private final Map<UUID, Client> uuidClients;
    private final Map<String, Client> nameClients;

    private final BotAutoAuthenticator autoAuthenticator;
    private final Function<AccountDto, Void> accountFunction;
    private final File accountsFile;

    private boolean ignoreSave;
    private Set<AccountDto> accounts;

    public CraftBotImpl(String fileName, List<AccountDto> accounts) throws BotAuthenticationFailedException {
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

        this.accounts = new HashSet<>(accounts);
        String outputFile = fileName;
        if (outputFile.endsWith(".txt")) {
            outputFile = outputFile + "-bot.json";
        }

        this.accountsFile = new File(outputFile);
        this.accountFunction = (account) -> {
            saveAccounts();
            return null;
        };

        autoAuthenticator = new BotAutoAuthenticator(this, accounts);
        autoAuthenticator.start();

        SCHEDULER.scheduleWithFixedDelay(() -> {
            for (Client client : getClients()) {
                try {
                    client.tick();
                } catch (Exception ex) {
                    LOGGER.error("Unexpected error", ex);
                }
            }
        }, 50L, 50L, TimeUnit.MILLISECONDS);
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

    @Override
    public Set<Client> getClients() {
        return new HashSet<>(uuidClients.values());
    }

    @Override
    public Optional<Client> findByName(String name) {
        return Optional.ofNullable(nameClients.get(name.toLowerCase()));
    }

    @Override
    public Optional<Client> findByUuid(UUID uuid) {
        return Optional.ofNullable(uuidClients.get(uuid));
    }

    public boolean isIgnoreSave() {
        return ignoreSave;
    }

    public void setIgnoreSave(boolean ignoreSave) {
        this.ignoreSave = ignoreSave;
    }

    public void saveAccounts() {
        if (ignoreSave) {
            return;
        }

        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("  ", DefaultIndenter.SYS_LF);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);

        List<AccountDto> accountList = new ArrayList<>(this.accounts);
        LOGGER.debug("Persisted Accounts list: {} | Accounts set: {}", accountList.size(), this.accounts.size());

        AccountsFileDto accountsFileDto = new AccountsFileDto();
        accountsFileDto.setAccounts(accountList);

        try {
            mapper.writer(printer).writeValue(accountsFile, accountsFileDto);
        } catch (IOException ex) {
            LOGGER.error("Unable to write accounts file (" + accountsFile.getName() + ")", ex);
        }
    }

    public synchronized void addAccount(AccountDto account) throws BotAuthenticationFailedException {
        if (account.getUniqueId() != null && uuidClients.containsKey(account.getUniqueId())) {
            LOGGER.info("Not adding account because it already exists");
            return;
        }

        addSession(new YggdrasilSession(account, accountFunction));

        accounts.add(account);
        accounts = new HashSet<>(accounts);
    }

    private void addSession(Session session) throws BotAuthenticationFailedException {
        addBot(new PlayClient(session));
    }

    private void addBot(Client client) {
        uuidClients.put(client.getUniqueId(), client);
        nameClients.put(client.getName().toLowerCase(), client);
    }

    public void setBot(CraftBot bot) {
        if (Bot.BOT != null) {
            throw new IllegalStateException("Bot already set");
        }

        Bot.BOT = bot;
    }

}
