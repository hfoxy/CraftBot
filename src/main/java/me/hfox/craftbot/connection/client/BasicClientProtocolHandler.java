package me.hfox.craftbot.connection.client;

import io.netty.channel.socket.SocketChannel;
import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.pipeline.ProtocolHandler;

public class BasicClientProtocolHandler extends ProtocolHandler {

    private final BasicClient client;

    public BasicClientProtocolHandler(BasicClient client) {
        this.client = client;
    }

    @Override
    protected Connection createConnection(SocketChannel channel) throws BotProtocolException {
        return client.createConnection(channel);
    }

}
