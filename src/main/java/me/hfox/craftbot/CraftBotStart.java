package me.hfox.craftbot;

import me.hfox.aphelion.Aphelion;
import me.hfox.craftbot.chat.ChatColour;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Console;
import me.hfox.craftbot.terminal.TerminalReader;
import me.hfox.craftbot.terminal.commands.ManagementCommands;
import me.hfox.craftbot.terminal.commands.PathingCommands;
import me.hfox.craftbot.terminal.commands.ServerCommands;
import me.hfox.craftbot.terminal.commands.WorldCommands;
import me.hfox.craftbot.world.palette.BlockPalette;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CraftBotStart {

    private static final Logger LOGGER = LoggerFactory.getLogger(CraftBotStart.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        AnsiConsole.systemInstall();

        CraftBot bot = new CraftBotImpl();
        LOGGER.info(ChatColour.AQUA + "Starting {}" + ChatColour.RED + " version {}", bot.getName(), bot.getVersion());
        BlockPalette.load();

        Console console = new Console();
        Aphelion<CommandSender> aphelion = new Aphelion<CommandSender>(CommandSender.class) {
            @Override
            public boolean hasPermission(CommandSender commandSender, String s) {
                return true;
            }
        };

        aphelion.getRegistration().register(ManagementCommands.class);
        aphelion.getRegistration().register(ServerCommands.class);
        aphelion.getRegistration().register(PathingCommands.class);
        aphelion.getRegistration().register(WorldCommands.class);

        TerminalReader terminal = new TerminalReader(console, aphelion);
        terminal.start();

        Ansi.ansi().reset();
    }

}
