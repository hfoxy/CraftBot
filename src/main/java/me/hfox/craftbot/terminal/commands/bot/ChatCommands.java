package me.hfox.craftbot.terminal.commands.bot;

import me.hfox.aphelion.command.CommandContext;
import me.hfox.aphelion.command.annotations.Command;
import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayChatMessage;
import me.hfox.craftbot.terminal.CommandSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatCommands {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatCommands.class);

    @Command(aliases = {"toggle-chat", "tc"}, description = "Toggles chat", max = 0)
    public static void toggleChat(Client sender, CommandContext<CommandSender> args) {
        sender.setChatEnabled(!sender.isChatEnabled());

        if (sender.isChatEnabled()) {
            sender.sendMessage("Chat enabled!");
        }
    }

    @Command(aliases = {"chat", "c"}, description = "Sends a message", min = 1)
    public static void chat(Client sender, CommandContext<CommandSender> args) {
        sender.sendMessage("Sending message: {}", args.getJoinedString());
        sender.getConnection().writePacket(new PacketClientPlayChatMessage(args.getJoinedString()));
    }

}
