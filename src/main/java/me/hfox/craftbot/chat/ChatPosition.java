package me.hfox.craftbot.chat;

import me.hfox.craftbot.exception.chat.BotUnknownChatPositionException;

public enum ChatPosition {

    CHAT,
    SYSTEM,
    HOTBAR;

    public static ChatPosition findById(int id) {
        if (id < 0 || id >= values().length) {
            throw new BotUnknownChatPositionException(Integer.toString(id));
        }

        return values()[id];
    }

}
