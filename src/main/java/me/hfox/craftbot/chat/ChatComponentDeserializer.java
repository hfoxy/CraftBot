package me.hfox.craftbot.chat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ChatComponentDeserializer extends StdDeserializer<StringSupportedChatComponent> {

    public ChatComponentDeserializer() {
        this(null);
    }

    public ChatComponentDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public StringSupportedChatComponent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String text = jsonParser.getValueAsString();
        if (text != null) {
            StringSupportedChatComponent result = new StringSupportedChatComponent();
            result.setText(text);
            return result;
        }

        ChatComponent chatComponent = jsonParser.getCodec().readValue(jsonParser, ChatComponent.class);
        StringSupportedChatComponent result = new StringSupportedChatComponent();
        result.setText(chatComponent.getText());
        result.setTranslate(chatComponent.getTranslate());
        result.setInsertion(chatComponent.getInsertion());
        result.setBold(chatComponent.isBold());
        result.setItalic(chatComponent.isItalic());
        result.setUnderlined(chatComponent.isUnderlined());
        result.setStrikethrough(chatComponent.isStrikethrough());
        result.setObfuscated(chatComponent.isObfuscated());
        result.setColour(chatComponent.getColour());
        result.setExtra(chatComponent.getExtra());
        return result;
    }

}
