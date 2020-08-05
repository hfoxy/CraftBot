package me.hfox.craftbot.protocol.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.protocol.ServerPacket;

import java.util.concurrent.*;

public class ConnectionPipe extends ChannelInboundHandlerAdapter {

    private final Connection connection;

    public ConnectionPipe(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        if (msg instanceof ServerPacket) {
            connection.handle((ServerPacket) msg);
        }
    }

}
