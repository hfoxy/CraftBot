package me.hfox.craftbot.terminal;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;

public class ColouredConsoleAppender extends ConsoleAppender<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent eventObject) {
        super.append(new ColourLoggingEvent(eventObject));
    }

}
