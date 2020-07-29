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
import me.hfox.craftbot.entity.ClientPlayer;
import me.hfox.craftbot.exception.connection.BotConnectionException;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.JavaProtocol;
import me.hfox.craftbot.protocol.Protocol;
import me.hfox.craftbot.protocol.pipeline.ProtocolHandler;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayKeepAlive;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayPlayerMovement;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayPlayerPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicClient implements Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicClient.class);

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    private final ClientPlayer clientPlayer = new ClientPlayer();

    private ProtocolHandler handler;

    private AtomicInteger ticker = new AtomicInteger();
    private AtomicInteger tickerZ = new AtomicInteger();
    private Future<?> tickTask;
    private boolean down;
    private boolean downZ;

    @Override
    public void connect(String host, int port) throws BotConnectionException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            handler = new BasicClientProtocolHandler(this);
            bootstrap.handler(handler);
            LOGGER.info("client init");

            // Start the client.
            ChannelFuture f = bootstrap.connect(host, port).sync();
            LOGGER.info("client started");

            onConnect(host, port);

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
            LOGGER.info("client stopped");
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
        return new ServerConnection(this, channel, createProtocol());
    }

    public ProtocolHandler getHandler() {
        return handler;
    }

    protected void onConnect(String host, int port) {
        // do nothing here
    }

    @Override
    public void completeLogin() {
        if (tickTask == null || tickTask.isCancelled()) {
            tickTask = executorService.scheduleAtFixedRate(() -> {
                Connection connection = getConnection();
                if (connection == null || !connection.isConnected()) {
                    tickTask.cancel(false);
                    return;
                }

                int valX;
                if (down) {
                    valX = ticker.getAndDecrement();
                } else {
                    valX = ticker.getAndIncrement();
                }

                if (valX <= 0) {
                    down = false;
                } else if (valX >= 40) {
                    down = true;
                }

                int valZ;
                if (down) {
                    valZ = ticker.getAndAdd(-2);
                } else {
                    valZ = ticker.getAndAdd(2);
                }

                if (valX <= 0) {
                    down = false;
                } else if (valX >= 40) {
                    down = true;
                }

                if (valX == 0) {
                    LOGGER.info("playing out player movement");
                    connection.writePacket(new PacketClientPlayPlayerMovement(true));
                }

                double diff = ((valX - 20) / 2D) * (2D / 40);
                double diffZ = ((valZ - 30) / 2D) * (2D / 60) * 1.5;
                LOGGER.info("moving {}, 0, {}", diff, diffZ);

                connection.writePacket(new PacketClientPlayPlayerPosition(clientPlayer.getLocation().getX() + diff, clientPlayer.getLocation().getY(), clientPlayer.getLocation().getZ() + diffZ, false));
            }, 0, 25, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public Connection getConnection() {
        if (handler != null) {
            return handler.getConnection();
        }

        return null;
    }

    @Override
    public ClientPlayer getClientPlayer() {
        return clientPlayer;
    }

}
