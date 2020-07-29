package me.hfox.craftbot.protocol.play.server;

import io.netty.buffer.Unpooled;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

class PacketServerPlayDeclareRecipesTest {

    @Test
    void testPacket() throws Exception {
        FileInputStream fis = new FileInputStream(new File("recipe_packet"));
        DataInputStream dis = new DataInputStream(fis);
        byte[] fileContent = new byte[dis.readInt()];
        for (int i = 0; i < fileContent.length; i++) {
            fileContent[i] = dis.readByte();
        }

        ProtocolBuffer buffer = new ProtocolBuffer(Unpooled.copiedBuffer(fileContent));
        PacketServerPlayDeclareRecipes recipesPacket = new PacketServerPlayDeclareRecipes();
        recipesPacket.read(buffer);
    }

}