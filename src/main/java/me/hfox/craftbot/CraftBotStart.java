package me.hfox.craftbot;

import me.hfox.aphelion.Aphelion;
import me.hfox.craftbot.auth.AccountDto;
import me.hfox.craftbot.chat.ChatColour;
import me.hfox.craftbot.connection.client.session.dto.SessionServerConfiguration;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import me.hfox.craftbot.terminal.CommandSender;
import me.hfox.craftbot.terminal.Console;
import me.hfox.craftbot.terminal.TerminalReader;
import me.hfox.craftbot.terminal.commands.*;
import me.hfox.craftbot.utils.AccountUtils;
import me.hfox.craftbot.world.palette.BlockPalette;
import org.apache.commons.cli.*;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class CraftBotStart {

    private static final Logger LOGGER = LoggerFactory.getLogger(CraftBotStart.class);

    public static void main(String[] args) throws IOException, BotAuthenticationFailedException {
        AnsiConsole.systemInstall();

        Options options = new Options();

        Option accountsFile = new Option("a", "accounts-file", true, "Path to input accounts file");
        accountsFile.setRequired(false);
        options.addOption(accountsFile);

        Option accountsOutputFile = new Option("A", "accounts-output-file", true, "Path to output accounts file");
        accountsOutputFile.setRequired(false);
        options.addOption(accountsOutputFile);

        Option sessionServerFile = new Option("S", "session-server-file", true, "Path to session server configuration file");
        sessionServerFile.setRequired(false);
        options.addOption(sessionServerFile);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            return;
        }

        Bot.SESSION_CONFIGURATION = AccountUtils.getSessionServerConfiguration(cmd.getOptionValue("session-server-file", "session-server.json"));

        String fileName = cmd.getOptionValue("accounts-file", "accounts.json");
        List<AccountDto> accounts = AccountUtils.getAccounts(fileName);
        if (accounts == null) {
            // error message should already have been handled
            return;
        }

        if (accounts.isEmpty()) {
            LOGGER.error("No accounts files exists. Please populate and restart");
            return;
        }

        CraftBot bot = new CraftBotImpl(cmd.getOptionValue("accounts-output-file", fileName), accounts);
        LOGGER.info(ChatColour.AQUA + "Starting {}" + ChatColour.RED + " version {}", bot.getName(), bot.getVersion());
        BlockPalette.load();

        Console console = new Console();
        Aphelion<CommandSender> aphelion = new Aphelion<CommandSender>(CommandSender.class) {
            @Override
            public boolean hasPermission(CommandSender commandSender, String s) {
                return true;
            }
        };

        aphelion.getRegistration().register(ManagementCommands.class);
        aphelion.getRegistration().register(BotCommands.class);

        TerminalReader terminal = new TerminalReader(console, aphelion);
        terminal.start();

        Ansi.ansi().reset();
    }

}
