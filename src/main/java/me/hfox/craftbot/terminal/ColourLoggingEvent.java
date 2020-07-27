package me.hfox.craftbot.terminal;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import me.hfox.craftbot.ChatColour;
import org.slf4j.Marker;

import java.util.Map;

public class ColourLoggingEvent implements ILoggingEvent {

    private final ILoggingEvent event;

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
        return event.getFormattedMessage() + ChatColour.RESET;
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
