package me.hfox.craftbot.entity;

import me.hfox.craftbot.chat.ChatComponent;

public interface CommandBlockMinecart extends MinecartBase {

    String getCommand();

    void setCommand(String command);

    ChatComponent getLastOutput();

    void setLastOutput(ChatComponent lastOutput);

}
