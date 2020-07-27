package me.hfox.craftbot.terminal;

public interface CommandSender {

    void sendMessage(Level level, String message, Object... args);

    void sendMessage(String message, Object... args);

    void sendException(Level level, String message, Throwable throwable);

    void sendException(String message, Throwable throwable);

}
