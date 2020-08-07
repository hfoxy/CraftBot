package me.hfox.craftbot.terminal.commands;

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
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Level;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BotCommands {

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(10);

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
    public static void execute(CommandSender sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException {
        CraftBot bot = Bot.getBot();

        Set<Client> targets = new HashSet<>();
        String targetString = args.getString(0);

        if (targetString.startsWith("#")) {
            String token = targetString.substring(1);
            if (token.equals("a")) {
                targets.addAll(bot.getClients());
            } else {
                throw new CommandUsageException("Unknown token: '" + token + "'");
            }
        } else {
            targets.add(bot.findByName(targetString).orElseThrow(() -> new CommandUsageException("Unknown client '" + targetString + "'")));
        }

        int delay = 1000;
        String cmd = args.getJoinedString(1);
        boolean delayed = cmd.startsWith("connect");

        if (delayed) {
            Iterator<Client> iterator = targets.iterator();
            DelayedCommand delayedCommand = new DelayedCommand();
            delayedCommand.future = SCHEDULER.scheduleWithFixedDelay(() -> {
                if (!iterator.hasNext()) {
                    delayedCommand.future.cancel(false);
                    return;
                }

                Client client = iterator.next();
                client.execute(cmd);
            }, 100, delay, TimeUnit.MILLISECONDS);
        } else {
            for (Client client : targets) {
                client.execute(cmd);
            }
        }

        /*int i = 0;
        for (Client client : targets) {
            if (delayed) {
                SCHEDULER.schedule(() -> client.execute(cmd), delay * i++, TimeUnit.MILLISECONDS);
            } else {
                client.execute(cmd);
            }
        }*/

        sender.sendMessage("Done! ('{}')", cmd);
    }

    @Command(aliases = {"accounts"}, description = "Lists accounts", max = 0)
    public static void accounts(CommandSender sender, CommandContext<CommandSender> args) throws CommandException {
        //
    }

    private static class DelayedCommand {

        private Future<?> future;

    }

}
