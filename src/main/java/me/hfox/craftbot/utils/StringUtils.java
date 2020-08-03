package me.hfox.craftbot.utils;

import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class StringUtils {

    public static String generateRandomString(String characters, int length) {
        return generateRandomString(characters.toCharArray(), length);
    }

    public static String generateRandomString(char[] chars, int length) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(chars[random.nextInt(0, length)]);
        }

        return builder.toString();
    }

    public static UUID toUuid(String value) {
        if (value.contains("-")) {
            return UUID.fromString(value);
        }

        StringJoiner joiner = new StringJoiner("-");
        joiner.add(value.substring(0, 8));
        joiner.add(value.substring(8, 12));
        joiner.add(value.substring(12, 16));
        joiner.add(value.substring(16, 20));
        joiner.add(value.substring(20));
        return UUID.fromString(joiner.toString());
    }

}
