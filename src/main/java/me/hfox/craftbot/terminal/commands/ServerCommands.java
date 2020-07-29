package me.hfox.craftbot.terminal.commands;

import me.hfox.aphelion.command.CommandContext;
import me.hfox.aphelion.command.annotations.Command;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.exception.CommandUsageException;
import me.hfox.craftbot.connection.client.PlayClient;
import me.hfox.craftbot.connection.client.StatusClient;
import me.hfox.craftbot.exception.connection.BotConnectionException;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Level;

public class ServerCommands {

    @Command(aliases = {"ping"}, description = "Ping a server", usage = "[host{:port}]", min = 1, max = 1)
    public static void ping(CommandSender sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException {
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

    @Command(aliases = {"connect"}, description = "Connect to a server", usage = "[host{:port}]", min = 1, max = 1)
    public static void connect(CommandSender sender, CommandContext<CommandSender> args) throws CommandException {
        String host = args.getString(0);
        int port = 25565;
        if (host.contains(":")) {
            String[] split = host.split(":");
            if (split.length != 2) {
                throw new CommandUsageException("Invalid form of host");
            }

            host = split[0];
        }

        sender.sendMessage(Level.INFO, "Attempting to connect to {}:{}", host, port);

        PlayClient playClient = new PlayClient();

        try {
            playClient.connect(host, port);
        } catch (BotConnectionException ex) {
            sender.sendException("Unable to connect", ex);
        }
    }

}
