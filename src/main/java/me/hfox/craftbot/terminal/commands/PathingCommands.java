package me.hfox.craftbot.terminal.commands;

import me.hfox.craftbot.pathing.AStar;
import me.hfox.craftbot.pathing.PathingResult;
import me.hfox.craftbot.pathing.Tile;
import me.hfox.aphelion.command.CommandContext;
import me.hfox.aphelion.command.annotations.Command;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.exception.CommandUsageException;
import me.hfox.craftbot.connection.client.PlayClient;
import me.hfox.craftbot.entity.living.ClientPlayer;
import me.hfox.craftbot.exception.connection.BotConnectionException;
import me.hfox.craftbot.handling.WorldHandler;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Level;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PathingCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathingCommands.class);

    public static WorldHandler WORLD_HANDLER;

    @Command(aliases = {"toggle-sprint", "sprint"}, description = "Toggles sprinting", max = 0)
    public static void toggleSprint(CommandSender sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException, AStar.InvalidPathException {
        ClientPlayer player = WORLD_HANDLER.getClientHandler().getPlayer();
        player.setSprinting(!player.isSprinting());

        sender.sendMessage("Player is {} sprinting ({})", player.isSprinting() ? "now" : "no longer", player.isSprinting());
    }

    @Command(aliases = {"path-recalculate", "p-rc"}, description = "Recalculate path lookup table", max = 0)
    public static void pathRecalculate(CommandSender sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException, AStar.InvalidPathException {
        WORLD_HANDLER.getClientHandler().getTickHandler().recalculateDiffLookupTables();
    }

    @Command(aliases = {"path"}, description = "Find a path", usage = "[x] [y] [z] {sprinting}", max = 4)
    public static void path(CommandSender sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException, AStar.InvalidPathException {
        if (WORLD_HANDLER == null) {
            sender.sendMessage(Level.ERROR, "Unable to path, no WorldHandler");
            return;
        }

        boolean sprint;
        Location end;
        if (args.length() < 3) {
            throw new CommandUsageException();
        } else {
            end = new Location(args.getInteger(0), args.getInteger(1), args.getInteger(2));
            LOGGER.info("End location is {}", end);
            sprint = args.getBoolean(3, false);
        }

        PathingResult result = WORLD_HANDLER.getClientHandler().path(end, 500);
        sender.sendMessage("Result: " + result);

        if (result == PathingResult.SUCCESS) {
            WORLD_HANDLER.getClientHandler().getPlayer().setSprinting(sprint);
        }
    }

}
