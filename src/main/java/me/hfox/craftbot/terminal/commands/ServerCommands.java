package me.hfox.craftbot.terminal.commands;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.hfox.aphelion.command.CommandContext;
import me.hfox.aphelion.command.annotations.Command;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.exception.CommandUsageException;
import me.hfox.craftbot.Bot;
import me.hfox.craftbot.protocol.handshake.client.PacketClientHandshake;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.pipeline.ProtocolClientHandler;
import me.hfox.craftbot.protocol.status.client.PacketClientStatusRequest;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Level;

public class ServerCommands {

    @Command(aliases = {"ping"}, description = "Ping a server", usage = "[host{:port}]", min = 1, max = 1)
    public static void about(CommandSender sender, CommandContext<CommandSender> args) throws CommandException, InterruptedException {
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

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);

            ProtocolClientHandler handler = new ProtocolClientHandler();
            b.handler(handler);
            sender.sendMessage("client init");

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();
            sender.sendMessage("client started");

            handler.getConnection().writePacket(new PacketClientHandshake(578, host, port, ProtocolState.STATUS));
            handler.getConnection().writePacket(new PacketClientStatusRequest());

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
            sender.sendMessage("client stopped");
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
