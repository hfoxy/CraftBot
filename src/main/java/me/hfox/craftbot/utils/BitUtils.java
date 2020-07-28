package me.hfox.craftbot.utils;

public final class BitUtils {

    public static boolean hasFlag(byte flags, int bit) {
        return (flags & bit) == bit;
    }

}
