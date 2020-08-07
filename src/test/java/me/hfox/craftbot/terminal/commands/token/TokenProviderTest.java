package me.hfox.craftbot.terminal.commands.token;

import me.hfox.craftbot.exception.token.TokenFormatException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TokenProviderTest {

    @Test
    void checkToken() throws TokenFormatException {
        char expectedToken = 'a';
        Map<String, String> expectedArgs = new HashMap<>();
        expectedArgs.put("limit", "100");

        TokenProvider provider = new TokenProvider("#a[limit=100]");
        assertEquals(expectedToken, provider.getToken());
        assertEquals(expectedArgs, provider.getArguments());
    }

    @Test
    void checkInvalidToken() {
        assertThrows(TokenFormatException.class, () -> new TokenProvider("a[limit=100]"));
    }

    @Test
    void checkMultiArgToken() throws TokenFormatException {
        char expectedToken = 'z';
        Map<String, String> expectedArgs = new HashMap<>();
        expectedArgs.put("limit", "100");
        expectedArgs.put("baguette", "ham");

        TokenProvider provider = new TokenProvider("#z[limit=100,baguette=ham]");
        assertEquals(expectedToken, provider.getToken());
        assertEquals(expectedArgs, provider.getArguments());
    }

}