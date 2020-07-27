package me.hfox.craftbot;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.hfox.aphelion.Aphelion;
import me.hfox.craftbot.protocol.handshake.client.PacketClientHandshake;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.pipeline.ProtocolClientHandler;
import me.hfox.craftbot.protocol.status.client.PacketClientStatusRequest;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Console;
import me.hfox.craftbot.terminal.TerminalReader;
import me.hfox.craftbot.terminal.commands.ManagementCommands;
import me.hfox.craftbot.terminal.commands.ServerCommands;
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

        Console console = new Console();
        Aphelion<CommandSender> aphelion = new Aphelion<CommandSender>(CommandSender.class) {
            @Override
            public boolean hasPermission(CommandSender commandSender, String s) {
                return true;
            }
        };

        aphelion.getRegistration().register(ManagementCommands.class);
        aphelion.getRegistration().register(ServerCommands.class);

        TerminalReader terminal = new TerminalReader(console, aphelion);
        terminal.start();

        Ansi.ansi().reset();
    }

}
