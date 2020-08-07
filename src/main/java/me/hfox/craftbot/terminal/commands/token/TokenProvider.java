package me.hfox.craftbot.terminal.commands.token;

import com.google.common.collect.ImmutableMap;
import me.hfox.craftbot.exception.token.TokenFormatException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenProvider {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("#([a-z])(\\[([a-zA-Z0-9-_=,]*)])?");
    private static final Pattern ARGUMENT_PATTERN = Pattern.compile(",?([a-zA-Z0-9_-]*)=([a-zA-Z0-9_-]*)");

    private final char token;
    private final Map<String, String> arguments;

    public TokenProvider(String text) throws TokenFormatException {
        Matcher tokenMatcher = TOKEN_PATTERN.matcher(text);
        if (!tokenMatcher.matches()) {
            throw new TokenFormatException("Invalid token");
        }

        token = tokenMatcher.group(1).charAt(0);

        Map<String, String> baseArguments = new HashMap<>();
        String argText = tokenMatcher.group(2);
        if (argText != null) {
            Matcher argumentMatched = ARGUMENT_PATTERN.matcher(argText);
            while (argumentMatched.find()) {
                String key = argumentMatched.group(1);
                String value = argumentMatched.group(2);
                baseArguments.put(key, value);
            }
        }

        arguments = new ImmutableMap.Builder<String, String>().putAll(baseArguments).build();
    }

    public char getToken() {
        return token;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public Optional<String> getStringArgument(String key) {
        return Optional.ofNullable(arguments.get(key));
    }

    public String getStringArgument(String key, String def) {
        return getStringArgument(key).orElse(def);
    }

    public Optional<Integer> getIntegerArgument(String key) {
        return getStringArgument(key).map(Integer::parseInt);
    }

    public int getIntegerArgument(String key, int def) {
        return getIntegerArgument(key).orElse(def);
    }

}
