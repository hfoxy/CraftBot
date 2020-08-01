package me.hfox.craftbot.terminal.commands;

import me.hfox.aphelion.command.CommandContext;
import me.hfox.aphelion.command.annotations.Command;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.craftbot.Bot;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayChatMessage;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Level;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.palette.BlockStateDto;

public class WorldCommands {

    @Command(aliases = {"block"}, description = "Find out about a block", usage = "[x] [y] [z]", min = 3, max = 3)
    public static void block(CommandSender sender, CommandContext<CommandSender> args) throws CommandException {
        int x = args.getInteger(0);
        int y = args.getInteger(1);
        int z = args.getInteger(2);
        Location location = new Location(x, y, z);

        BlockStateDto block = PathingCommands.WORLD_HANDLER.getWorld().getBlock(location);
        PathingCommands.WORLD_HANDLER.getClientHandler().getClient().getConnection().writePacket(new PacketClientPlayChatMessage("Block at [" + x + ", " + y + ", " + z + "] is '" + block.getBlock().getIdentifier() + "'"));
    }

}
