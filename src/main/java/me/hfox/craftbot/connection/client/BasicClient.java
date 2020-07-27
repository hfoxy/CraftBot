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
import me.hfox.craftbot.exception.connection.BotConnectionException;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.JavaProtocol;
import me.hfox.craftbot.protocol.Protocol;
import me.hfox.craftbot.protocol.pipeline.ProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicClient implements Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicClient.class);

    private ProtocolHandler handler;

    @Override
    public void connect(String host, int port) throws BotConnectionException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);

            handler = new BasicClientProtocolHandler(this);
            b.handler(handler);
            LOGGER.debug("client init");

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();
            LOGGER.debug("client started");

            onConnect(host, port);

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
            LOGGER.debug("client stopped");
        } catch (Exception ex) {
            throw new BotConnectionException("Unable to connect", ex);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    protected Protocol createProtocol() throws BotProtocolException {
        return new JavaProtocol();
    }

    public Connection createConnection(SocketChannel channel) throws BotProtocolException {
        return new ServerConnection(channel, createProtocol());
    }

    public ProtocolHandler getHandler() {
        return handler;
    }

    protected void onConnect(String host, int port) {
        // do nothing here
    }

    @Override
    public Connection getConnection() {
        if (handler != null) {
            return handler.getConnection();
        }

        return null;
    }

}
