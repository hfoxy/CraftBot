package me.hfox.craftbot.terminal.commands;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import me.hfox.aphelion.command.CommandContext;
import me.hfox.aphelion.command.annotations.Command;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.exception.CommandUsageException;
import me.hfox.craftbot.Bot;
import me.hfox.craftbot.CraftBot;
import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.connection.client.StatusClient;
import me.hfox.craftbot.exception.connection.BotConnectionException;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import me.hfox.craftbot.exception.token.TokenFormatException;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Level;
import me.hfox.craftbot.terminal.commands.token.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

public class BotCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotCommands.class);

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("connect-pool-%d").build();
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(10, THREAD_FACTORY);

    @Command(aliases = {"ping"}, description = "Ping a server", usage = "[host{:port}]", min = 1, max = 1)
    public static void ping(CommandSender sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException, BotAuthenticationFailedException {
        String host = args.getString(0);
        int port = 25565;
        if (host.contains(":")) {
            String[] split = host.split(":");
            if (split.length != 2) {
                throw new CommandUsageException("Invalid form of host");
            }

            host = split[0];
        }

        sender.sendMessage(Level.INFO, "Attempting to ping {}:{}", host, port);

        StatusClient statusClient = new StatusClient();

        try {
            statusClient.connect(host, port);
        } catch (BotConnectionException ex) {
            sender.sendException("Unable to connect", ex);
        }
    }

    @Command(aliases = {"execute"}, description = "Execute commands on a bot", usage = "[bot name] [command]", min = 2)
    public static void execute(CommandSender sender, CommandContext<CommandSender> args) throws CommandException {
        Set<Client> targets = getTargets(args.getString(0));

        String cmd = args.getJoinedString(1);
        boolean connect = cmd.startsWith("connect");

        if (connect && targets.size() > 1) {
            throw new CommandUsageException("Connect is not supported this way");
        } else {
            for (Client client : targets) {
                client.execute(cmd);
            }
        }

        sender.sendMessage("Done! ('{}')", cmd);
    }

    @Command(aliases = {"accounts"}, description = "Lists accounts", max = 0)
    public static void accounts(CommandSender sender, CommandContext<CommandSender> args) throws CommandException {
        //
    }

    @Command(aliases = {"connect"}, description = "Connects to a server", min = 2)
    public static void connect(CommandSender sender, CommandContext<CommandSender> args) throws CommandException {
        String host = args.getString(1);
        int port = 25565;
        if (host.contains(":")) {
            String[] split = host.split(":");
            if (split.length != 2) {
                throw new CommandUsageException("Invalid form of host");
            }

            host = split[0];
            port = Integer.parseInt(split[1]);
        }

        Set<Client> targets = getTargets(args.getString(0));
        Iterator<Client> iterator = targets.iterator();
        loopConnect(iterator, host, port);

        /*Set<Client> targets = getTargets(args.getString(0));
        Iterator<Client> iterator = targets.iterator();
        DelayedCommand delayedCommand = new DelayedCommand();
        delayedCommand.future = SCHEDULER.scheduleWithFixedDelay(() -> {
            if (!iterator.hasNext()) {
                delayedCommand.future.cancel(false);
                return;
            }

            Client client = iterator.next();
            client.connect("fw01.forthwindmc.net", 25500);
        }, 100, delay, TimeUnit.MILLISECONDS);*/
    }

    private static void loopConnect(Iterator<Client> iterator, String host, int port) {
        SCHEDULER.schedule(() -> {
            try {
                if (iterator.hasNext()) {
                    Client client = iterator.next();
                    LOGGER.info("Connecting {} to {}:{}", client.getName(), host, port);

                    client.connect(host, port, () -> {
                        loopConnect(iterator, host, port);
                    });
                }
            } catch (Exception ex) {
                LOGGER.error("Error connecting", ex);
            }
        }, 50L, TimeUnit.MILLISECONDS);
    }

    private static Set<Client> getTargets(String targetString) throws CommandUsageException {
        CraftBot bot = Bot.getBot();
        Set<Client> targets = new HashSet<>();

        if (targetString.startsWith("#")) {
            TokenProvider provider = new TokenProvider(targetString);
            if (provider.getToken() == 'a') {
                int limit = provider.getIntegerArgument("limit", -1);
                Set<Client> add = bot.getClients();
                if (limit >= 0) {
                    Set<Client> limited = new HashSet<>();

                    int i = 0;
                    for (Client client : add) {
                        if (i++ >= limit) {
                            break;
                        }

                        limited.add(client);
                    }

                    add = limited;
                }

                targets.addAll(add);
            } else {
                throw new CommandUsageException("Unknown token: '" + provider.getToken() + "'");
            }
        } else {
            targets.add(bot.findByName(targetString).orElseThrow(() -> new CommandUsageException("Unknown client '" + targetString + "'")));
        }

        return targets;
    }

    private static class DelayedCommand {

        private Future<?> future;

    }

}
