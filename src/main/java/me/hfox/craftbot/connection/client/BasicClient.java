package me.hfox.craftbot.connection.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.hfox.aphelion.Aphelion;
import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.connection.ServerConnection;
import me.hfox.craftbot.connection.client.session.Session;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.exception.connection.BotConnectionException;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import me.hfox.craftbot.protocol.JavaProtocol;
import me.hfox.craftbot.protocol.Protocol;
import me.hfox.craftbot.protocol.pipeline.ProtocolHandler;
import me.hfox.craftbot.terminal.Console;
import me.hfox.craftbot.terminal.Level;
import me.hfox.craftbot.terminal.TerminalReader;
import me.hfox.craftbot.terminal.commands.bot.ChatCommands;
import me.hfox.craftbot.terminal.commands.bot.PathingCommands;
import me.hfox.craftbot.terminal.commands.bot.ServerCommands;
import me.hfox.craftbot.terminal.commands.bot.WorldCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public class BasicClient<C extends Client> implements Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicClient.class);

    private final Session session;
    private final Console console;
    private final Aphelion<C> aphelion;

    private ProtocolHandler handler;
    private boolean chatEnabled;

    public BasicClient(Class<C> clazz, Session session) throws BotAuthenticationFailedException {
        this.session = session;
        this.session.authenticate();

        this.console = new Console(session.getName());
        this.aphelion = new Aphelion<C>(clazz) {
            @Override
            public boolean hasPermission(C commandSender, String s) {
                return true;
            }
        };

        this.chatEnabled = true;
        aphelion.getRegistration().register(ChatCommands.class);
        aphelion.getRegistration().register(ServerCommands.class);
        aphelion.getRegistration().register(PathingCommands.class);
        aphelion.getRegistration().register(WorldCommands.class);
    }

    @SuppressWarnings("unchecked")
    public C getMe() {
        return (C) this;
    }

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
            LOGGER.debug("client init");

            // Start the client.
            ChannelFuture channel = bootstrap.connect(host, port).sync();
            LOGGER.debug("client started");

            onConnect(host, port);
            safe = true;

            Thread th = new Thread(() -> {
                try {
                    // Wait until the connection is closed.
                    channel.channel().closeFuture().sync();
                    LOGGER.debug("client stopped");
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
    public Session getSession() {
        return session;
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

    @Override
    public boolean isChatEnabled() {
        return chatEnabled;
    }

    @Override
    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    @Override
    public void sendMessage(Level level, String message, Object... args) {
        console.sendMessage(level, message, args);
    }

    @Override
    public void sendMessage(String message, Object... args) {
        console.sendMessage(message, args);
    }

    @Override
    public void sendException(Level level, String message, Throwable throwable) {
        console.sendException(level, message, throwable);
    }

    @Override
    public void sendException(String message, Throwable throwable) {
        console.sendException(message, throwable);
    }

    @Override
    public void execute(String command) {
        TerminalReader.handleCommand(command, aphelion, getMe());
    }

}
