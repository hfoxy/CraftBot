package me.hfox.craftbot.terminal.commands;

import me.hfox.aphelion.command.CommandContext;
import me.hfox.aphelion.command.annotations.Command;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.craftbot.Bot;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Level;

public class ManagementCommands {

    @Command(aliases = {"about"}, description = "About the bot", max = 0)
    public static void about(CommandSender sender, CommandContext<CommandSender> args) throws CommandException {
        sender.sendMessage(Level.INFO, "Running {} version {}", Bot.getBot().getName(), Bot.getBot().getVersion());
    }

    @Command(aliases = {"stop", "end"}, description = "Stops the bot", max = 0)
    public static void stop(CommandSender sender, CommandContext<CommandSender> args) throws CommandException {
        sender.sendMessage(Level.INFO, "Bot shutting down!");
        System.exit(0);
    }

}
