package me.hfox.craftbot.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatComponent {

    private String text;
    private String translate;
    private String insertion;
    private List<String> with;

    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private boolean strikethrough;
    private boolean obfuscated;

    @JsonProperty("color")
    private String colour;

    private List<ChatComponent> extra;

    @JsonIgnore
    private String computedOutput;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.computedOutput = null;
        this.text = text;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.computedOutput = null;
        this.translate = translate;
    }

    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public List<String> getWith() {
        return with;
    }

    public void setWith(List<String> with) {
        this.with = with;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.computedOutput = null;
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.computedOutput = null;
        this.italic = italic;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public void setUnderlined(boolean underlined) {
        this.computedOutput = null;
        this.underlined = underlined;
    }

    public boolean isStrikethrough() {
        return strikethrough;
    }

    public void setStrikethrough(boolean strikethrough) {
        this.computedOutput = null;
        this.strikethrough = strikethrough;
    }

    public boolean isObfuscated() {
        return obfuscated;
    }

    public void setObfuscated(boolean obfuscated) {
        this.computedOutput = null;
        this.obfuscated = obfuscated;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.computedOutput = null;
        this.colour = colour;
    }

    public List<ChatComponent> getExtra() {
        return extra;
    }

    public void setExtra(List<ChatComponent> extra) {
        this.computedOutput = null;
        this.extra = extra;
    }

    public ChatColour getChatColour() {
        return ChatColour.findByName(colour);
    }

    public Set<ChatColour> getFormatChatColours() {
        Set<ChatColour> formatChatColours = new HashSet<>();

        if (bold) {
            formatChatColours.add(ChatColour.BOLD);
        } else if (italic) {
            formatChatColours.add(ChatColour.ITALIC);
        } else if (underlined) {
            formatChatColours.add(ChatColour.UNDERLINE);
        } else if (strikethrough) {
            formatChatColours.add(ChatColour.STRIKETHROUGH);
        } else if (obfuscated) {
            formatChatColours.add(ChatColour.MAGIC);
        }

        return formatChatColours;
    }

    @Override
    public String toString() {
        if (computedOutput != null) {
            return computedOutput;
        }

        StringBuilder builder = new StringBuilder();
        ChatColour colour = getChatColour();
        if (colour != null) {
            builder.append(colour);
        }

        for (ChatColour fcc : getFormatChatColours()) {
            builder.append(fcc);
        }

        if (text != null) {
            builder.append(text);
        } else if (translate != null && with != null) {
            for (String w : with) {
                builder.append(w);
            }
        } else if (translate != null) {
            builder.append(translate);
        }

        if (extra != null) {
            for (ChatComponent cc : extra) {
                builder.append(cc.toString());
            }
        }

        computedOutput = builder.toString();
        return computedOutput;
    }

}
