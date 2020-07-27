package me.hfox.craftbot.terminal;

import me.hfox.aphelion.Aphelion;
import me.hfox.aphelion.command.CommandHandler;
import me.hfox.aphelion.exception.CommandException;
import me.hfox.aphelion.exception.CommandUsageException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TerminalReader extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalReader.class);

    private final Console console;
    private final Aphelion<CommandSender> aphelion;

    private final Terminal terminal;
    private final LineReader lineReader;

    public TerminalReader(Console console, Aphelion<CommandSender> aphelion) throws IOException {
        this.console = console;
        this.aphelion = aphelion;
        this.terminal = TerminalBuilder.terminal();
        this.lineReader = LineReaderBuilder.builder()
                .terminal(this.terminal)
                .history(new DefaultHistory())
                .build();
    }

    @Override
    public void run() {
        String line;
        while ((line = lineReader.readLine()) != null) {
            handle(line);
        }
    }

    private void handle(String line) {
        if (line.equals("")) {
            return;
        }

        String[] split = line.split(" ");
        String[] args = new String[split.length - 1];
        if (args.length > 0) {
            System.arraycopy(split, 1, args, 0, args.length);
        }

        CommandHandler<CommandSender> handler = aphelion.getCommand(split[0]);
        if (handler == null) {
            LOGGER.warn("Unknown command '{}'", split[0]);
            return;
        }

        try {
            handler.invoke(aphelion, console, split[0], args);
        } catch (CommandUsageException ex) {
            if (ex.getMessage() != null) {
                LOGGER.error("Invalid command usage: {}", ex.getMessage());
            } else {
                LOGGER.error("Invalid command usage");
            }

            LOGGER.error("{} {}", split[0], handler.getUsage());
        } catch (CommandException ex) {
            LOGGER.error("Unable to parse command", ex);
        }
    }

}
