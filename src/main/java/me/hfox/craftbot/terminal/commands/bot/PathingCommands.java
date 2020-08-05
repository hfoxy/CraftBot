package me.hfox.craftbot.terminal.commands.bot;

import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.connection.client.PlayClient;
import me.hfox.craftbot.pathing.AStar;
import me.hfox.craftbot.pathing.PathingResult;
import me.hfox.aphelion.command.CommandContext;
import me.hfox.aphelion.command.annotations.Command;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.exception.CommandUsageException;
import me.hfox.craftbot.entity.living.ClientPlayer;
import me.hfox.craftbot.handling.WorldHandler;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Level;
import me.hfox.craftbot.world.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathingCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathingCommands.class);

    @Command(aliases = {"toggle-sprint", "sprint"}, description = "Toggles sprinting", max = 0)
    public static void toggleSprint(PlayClient sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException, AStar.InvalidPathException {
        ClientPlayer player = sender.getClientHandler().getPlayer();
        player.setSprinting(!player.isSprinting());

        sender.sendMessage("Player is {} sprinting ({})", player.isSprinting() ? "now" : "no longer", player.isSprinting());
    }

    @Command(aliases = {"path-recalculate", "p-rc"}, description = "Recalculate path lookup table", max = 0)
    public static void pathRecalculate(PlayClient sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException, AStar.InvalidPathException {
        sender.getClientHandler().getTickHandler().recalculateDiffLookupTables();
    }

    @Command(aliases = {"path"}, description = "Find a path", usage = "[x] [y] [z] {sprinting}", max = 4)
    public static void path(PlayClient sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException, AStar.InvalidPathException {
        boolean sprint;
        Location end;
        if (args.length() < 3) {
            throw new CommandUsageException();
        } else {
            end = new Location(args.getInteger(0), args.getInteger(1), args.getInteger(2));
            LOGGER.info("End location is {}", end);
            sprint = args.getBoolean(3, false);
        }

        PathingResult result = sender.getClientHandler().path(end, 300);
        sender.sendMessage("Result: " + result);

        if (result == PathingResult.SUCCESS) {
            sender.getClientHandler().getPlayer().setSprinting(sprint);
        }
    }

}
