package me.hfox.craftbot.terminal.commands.bot;

import me.hfox.aphelion.command.CommandContext;
import me.hfox.aphelion.command.annotations.Command;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.exception.CommandUsageException;
import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.connection.client.PlayClient;
import me.hfox.craftbot.connection.client.StatusClient;
import me.hfox.craftbot.connection.client.session.SessionService;
import me.hfox.craftbot.exception.connection.BotConnectionException;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Level;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ServerCommands {

    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(2000);

    @Command(aliases = {"connect"}, description = "Connect to a server", usage = "[host{:port}]", min = 1, max = 1)
    public static void connect(Client sender, CommandContext<CommandSender> args) throws CommandException, BotAuthenticationFailedException {
        String host = args.getString(0);
        int port = 25565;
        if (host.contains(":")) {
            String[] split = host.split(":");
            if (split.length != 2) {
                throw new CommandUsageException("Invalid form of host");
            }

            host = split[0];
            port = Integer.parseInt(split[1]);
        }

        sender.sendMessage(Level.INFO, "Attempting to connect to {}:{}", host, port);

        String hostF = host;
        int portF = port;
        SCHEDULER.execute(() -> {
            try {
                sender.connect(hostF, portF);
            } catch (BotConnectionException ex) {
                sender.sendException("Unable to connect", ex);
            }
        });
    }

}
