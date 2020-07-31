package me.hfox.craftbot.terminal.commands;

import me.hfox.craftbot.pathing.AStar;
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

    @Command(aliases = {"path"}, description = "Find a path", max = 3)
    public static void path(CommandSender sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException, AStar.InvalidPathException {
        if (WORLD_HANDLER == null) {
            sender.sendMessage(Level.ERROR, "Unable to path, no WorldHandler");
            return;
        }

        // -239 104 141
        Location end = new Location(-239, 104, 141);
        if (args.length() > 0) {
            if (args.length() != 3) {
                throw new CommandUsageException();
            } else {
                end = new Location(args.getInteger(0), args.getInteger(1), args.getInteger(2));
            }
        }

        World world = WORLD_HANDLER.getWorld();
        ClientPlayer clientPlayer = WORLD_HANDLER.getClientHandler().getPlayer();
        Location start = clientPlayer.getLocation();

        AStar path = new AStar(world, start, end, 50);
        List<Tile> route = path.iterate();
        sender.sendMessage("Result: " + path.getPathingResult());

        LOGGER.info("Path result: {}", route);
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
