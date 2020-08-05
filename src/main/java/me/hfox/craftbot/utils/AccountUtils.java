package me.hfox.craftbot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hfox.craftbot.CraftBotStart;
import me.hfox.craftbot.auth.AccountDto;
import me.hfox.craftbot.auth.AccountsFileDto;
import me.hfox.craftbot.connection.client.session.dto.SessionServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class AccountUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountUtils.class);

    public static SessionServerConfiguration getSessionServerConfiguration(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            Files.copy(CraftBotStart.class.getResourceAsStream("/session-server.json"), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            LOGGER.error("No session server configuration file ({}) exists - created one", file.getName());
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, SessionServerConfiguration.class);
    }

    public static List<AccountDto> getAccounts(String fileName) throws IOException {
        boolean json = true;
        if (!fileName.endsWith(".json")) {
            json = false;
        }

        File accountsFile = new File(fileName);
        if (!accountsFile.exists()) {
            if (json) {
                Files.copy(CraftBotStart.class.getResourceAsStream("/accounts.json"), accountsFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                LOGGER.error("No accounts file ({}) exists - created one. Please populate and restart", accountsFile.getName());
            } else {
                LOGGER.error("No accounts file ({}) exists. Please create one, populate it and restart", accountsFile.getName());
            }

            return null;
        }

        AccountsFileDto accountsDto;
        if (json) {
            ObjectMapper mapper = new ObjectMapper();
            accountsDto = mapper.readValue(accountsFile, AccountsFileDto.class);
        } else {
            List<String> lines = Files.readAllLines(accountsFile.toPath());

            accountsDto = new AccountsFileDto();
            List<AccountDto> accountDtos = new ArrayList<>();
            for (String line : lines) {
                String[] split = line.split(":");
                AccountDto accountDto = new AccountDto();
                accountDto.setUsername(split[0]);
                accountDto.setPassword(split[1]);
                accountDtos.add(accountDto);
            }

            accountsDto.setAccounts(accountDtos);
        }

        return accountsDto.getAccounts();
    }

}
