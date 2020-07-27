package me.hfox.craftbot.chat;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Attribute;
import org.fusesource.jansi.Ansi.Color;

import static org.fusesource.jansi.Ansi.ansi;

public enum ChatColour {

    BLACK(0x0, ansi().a(Attribute.RESET).fg(Color.BLACK).boldOff()),
    DARK_BLUE(0x1, ansi().a(Attribute.RESET).fg(Color.BLUE).boldOff()),
    DARK_GREEN(0x2, ansi().a(Attribute.RESET).fg(Color.GREEN).boldOff()),
    DARK_AQUA(0x3, ansi().a(Attribute.RESET).fg(Color.CYAN).boldOff()),
    DARK_RED(0x4, ansi().a(Attribute.RESET).fg(Color.RED).boldOff()),
    DARK_PURPLE(0x5, ansi().a(Attribute.RESET).fg(Color.MAGENTA).boldOff()),
    GOLD(0x6, ansi().a(Attribute.RESET).fg(Color.YELLOW).boldOff()),
    GRAY(0x7, ansi().a(Attribute.RESET).fg(Color.WHITE).boldOff()),
    DARK_GRAY(0x8, ansi().a(Attribute.RESET).fg(Color.BLACK).bold()),
    BLUE(0x9, ansi().a(Attribute.RESET).fg(Color.BLUE).bold()),
    GREEN(0xA, ansi().a(Attribute.RESET).fg(Color.GREEN).bold()),
    AQUA(0xB, ansi().a(Attribute.RESET).fg(Color.CYAN).bold()),
    RED(0xC, ansi().a(Attribute.RESET).fg(Color.RED).bold()),
    LIGHT_PURPLE(0xD, ansi().a(Attribute.RESET).fg(Color.MAGENTA).bold()),
    YELLOW(0xE, ansi().a(Attribute.RESET).fg(Color.YELLOW).bold()),
    WHITE(0xF, ansi().a(Attribute.RESET).fg(Color.WHITE).bold()),
    MAGIC('k', 0x10, false, ansi().a(Attribute.BLINK_SLOW)),
    BOLD('l', 0x11, false, ansi().a(Attribute.UNDERLINE_DOUBLE)),
    STRIKETHROUGH('m', 0x12, false, ansi().a(Attribute.STRIKETHROUGH_ON)),
    UNDERLINE('n', 0x13, false, ansi().a(Attribute.UNDERLINE)),
    ITALIC('o', 0x14, false, ansi().a(Attribute.ITALIC)),
    RESET('r', 0x15, ansi().a(Attribute.RESET));

    public static final char SPECIAL_TOKEN = '\u00A7';

    private final char key;
    private final int keyCode;
    private final boolean reset;
    private final String jansiReplacement;

    ChatColour(int keyCode, Ansi jansiReplacement) {
        this(keyCode, true, jansiReplacement);
    }

    ChatColour(int keyCode, boolean reset, Ansi jansiReplacement) {
        this(Integer.toHexString(keyCode).toCharArray()[0], keyCode, reset, jansiReplacement);
    }

    ChatColour(char key, int keyCode, Ansi jansiReplacement) {
        this(key, keyCode, true, jansiReplacement);
    }

    ChatColour(char key, int keyCode, boolean reset, Ansi jansiReplacement) {
        this.key = key;
        this.keyCode = keyCode;
        this.reset = reset;
        this.jansiReplacement = jansiReplacement.toString();
    }

    public char getKey() {
        return key;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isReset() {
        return reset;
    }

    public String getJansiReplacement() {
        return jansiReplacement;
    }

    public static ChatColour findByChar(char ch) {
        for (ChatColour colour : values()) {
            if (colour.getKey() == ch) {
                return colour;
            }
        }

        return null;
    }

    public static ChatColour findByName(String name) {
        for (ChatColour colour : values()) {
            if (colour.name().equalsIgnoreCase(name)) {
                return colour;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return jansiReplacement;
    }

}
