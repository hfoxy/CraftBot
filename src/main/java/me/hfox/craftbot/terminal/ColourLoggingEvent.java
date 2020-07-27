package me.hfox.craftbot.terminal;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import me.hfox.craftbot.chat.ChatColour;
import org.slf4j.Marker;

import java.util.Map;

import static me.hfox.craftbot.chat.ChatColour.RESET;
import static me.hfox.craftbot.chat.ChatColour.SPECIAL_TOKEN;

public class ColourLoggingEvent implements ILoggingEvent {

    private final ILoggingEvent event;

    private String formattedMessage;

    public ColourLoggingEvent(ILoggingEvent event) {
        this.event = event;
    }

    @Override
    public String getThreadName() {
        return event.getThreadName();
    }

    @Override
    public Level getLevel() {
        return event.getLevel();
    }

    @Override
    public String getMessage() {
        return event.getMessage();
    }

    @Override
    public Object[] getArgumentArray() {
        return event.getArgumentArray();
    }

    @Override
    public String getFormattedMessage() {
        if (formattedMessage != null) {
            return formattedMessage;
        }

        String msg = event.getFormattedMessage();
        if (msg.contains(SPECIAL_TOKEN + "")) {
            String[] split = msg.split(SPECIAL_TOKEN + "");

            StringBuilder builder = new StringBuilder();

            int index = 0;
            for (String str : split) {
                if (str.length() > 0) {
                    ChatColour clr = ChatColour.findByChar(str.charAt(0));
                    if (clr != null) {
                        builder.append(clr).append(str.substring(1));
                        continue;
                    }
                }

                if (index != 0) {
                    builder.append(SPECIAL_TOKEN);
                }

                builder.append(str);
                index++;
            }

            msg = builder.toString();
        }

        formattedMessage = msg + RESET;
        return formattedMessage;
    }

    @Override
    public String getLoggerName() {
        return event.getLoggerName();
    }

    @Override
    public LoggerContextVO getLoggerContextVO() {
        return event.getLoggerContextVO();
    }

    @Override
    public IThrowableProxy getThrowableProxy() {
        return event.getThrowableProxy();
    }

    @Override
    public StackTraceElement[] getCallerData() {
        return event.getCallerData();
    }

    @Override
    public boolean hasCallerData() {
        return event.hasCallerData();
    }

    @Override
    public Marker getMarker() {
        return event.getMarker();
    }

    @Override
    public Map<String, String> getMDCPropertyMap() {
        return event.getMDCPropertyMap();
    }

    @Override
    public Map<String, String> getMdc() {
        return event.getMdc();
    }

    @Override
    public long getTimeStamp() {
        return event.getTimeStamp();
    }

    @Override
    public void prepareForDeferredProcessing() {
        event.prepareForDeferredProcessing();
    }

}
