package me.hfox.craftbot.connection.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.connection.ServerConnection;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.exception.connection.BotConnectionException;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.handling.ClientHandler;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.JavaProtocol;
import me.hfox.craftbot.protocol.Protocol;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginSuccess;
import me.hfox.craftbot.protocol.pipeline.ProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

public class BasicClient implements Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicClient.class);

    private ProtocolHandler handler;

    @Override
    public void connect(String host, int port) throws BotConnectionException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        boolean safe = false;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            handler = new BasicClientProtocolHandler(this);
            bootstrap.handler(handler);
            LOGGER.info("client init");

            // Start the client.
            ChannelFuture channel = bootstrap.connect(host, port).sync();
            LOGGER.info("client started");

            onConnect(host, port);
            safe = true;

            Thread th = new Thread(() -> {
                try {
                    // Wait until the connection is closed.
                    channel.channel().closeFuture().sync();
                    LOGGER.info("client stopped");
                } catch (Exception ex) {
                    LOGGER.error("Unable to stop safely");
                } finally {
                    workerGroup.shutdownGracefully();
                }
            });

            th.start();
        } catch (Exception ex) {
            throw new BotConnectionException("Unable to connect", ex);
        } finally {
            if (!safe) {
                workerGroup.shutdownGracefully();
            }
        }
    }

    public ProtocolHandler getHandler() {
        return handler;
    }

    protected void onConnect(String host, int port) {
        // do nothing here
    }

    protected Protocol createProtocol() throws BotProtocolException {
        return new JavaProtocol();
    }

    public Connection createConnection(SocketChannel channel) throws BotProtocolException {
        return new ServerConnection(this, channel, createProtocol());
    }

    @Override
    public Connection getConnection() {
        if (handler != null) {
            return handler.getConnection();
        }

        return null;
    }

    @Override
    public UUID getUniqueId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<UUID, PlayerInfo> getKnownPlayers() {
        throw new UnsupportedOperationException();
    }

}
