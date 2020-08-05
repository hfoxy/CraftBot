package me.hfox.craftbot.auth;

import me.hfox.craftbot.Bot;
import me.hfox.craftbot.CraftBotImpl;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BotAutoAuthenticator implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotAutoAuthenticator.class);

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    private final Object lock = new Object();

    private final AtomicInteger authenticated;
    private final AtomicInteger totalAccounts;

    private final CraftBotImpl bot;
    private final List<AccountDto> accounts;

    private ScheduledFuture<?> future;

    public BotAutoAuthenticator(CraftBotImpl bot, List<AccountDto> accounts) {
        this.authenticated = new AtomicInteger();
        this.totalAccounts = new AtomicInteger();

        this.bot = bot;
        this.accounts = new ArrayList<>(accounts);
    }

    public void start() {
        if (future != null && !future.isCancelled()) {
            return;
        }

        future = executorService.scheduleWithFixedDelay(
                this, 50L, Bot.getSessionConfiguration().getCheckIntervalMillis(), TimeUnit.MILLISECONDS
        );
    }

    public void addAccounts(List<AccountDto> accounts) {
        synchronized (lock) {
            this.accounts.addAll(accounts);
        }
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                int checked = 0;
                Set<AccountDto> preAuthd = new HashSet<>();
                for (AccountDto account : accounts) {
                    if (account.hasBeenAuthenticated()) {
                        try {
                            bot.addAccount(account);
                            authenticated.incrementAndGet();
                            preAuthd.add(account);
                        } catch (BotAuthenticationFailedException ex) {
                            LOGGER.debug("Unable to authenticate", ex);
                        } catch (Exception ex) {
                            LOGGER.error("Unexpected error", ex);
                        } finally {
                            totalAccounts.incrementAndGet();
                        }
                    } else if (account.isInvalidDetails()) {
                        // account has invalid details, don't use this account until the user corrects it
                        // LOGGER.info("Found account that has invalid credentials: {}", account.getUsername());
                        totalAccounts.incrementAndGet();
                        preAuthd.add(account);
                    }

                    checked++;
                }

                accounts.removeAll(preAuthd);
                LOGGER.debug("Checked {} accounts and found {} to filter out", checked, preAuthd.size());

                if (accounts.isEmpty()) {
                    LOGGER.debug("Accounts list is empty!");
                    return;
                }

                int pull = Bot.getSessionConfiguration().getAccountsPerCheck();
                if (pull > accounts.size()) {
                    pull = accounts.size();
                }

                int success = 0;
                List<AccountDto> subset = accounts.subList(0, pull);
                int pulled = subset.size();

                bot.setIgnoreSave(true);
                for (AccountDto account : subset) {
                    try {
                        bot.addAccount(account);
                        authenticated.incrementAndGet();
                        LOGGER.debug("Authenticated with {}", account.getName());
                        success++;
                    } catch (BotAuthenticationFailedException ex) {
                        LOGGER.debug("Unable to authenticate", ex);
                        // LOGGER.info("Unable to authenticate with {} ({})", account.getUsername(), account.isInvalidDetails());
                    } catch (Exception ex) {
                        LOGGER.error("Unexpected error", ex);
                    } finally {
                        totalAccounts.incrementAndGet();
                    }
                }

                bot.setIgnoreSave(false);

                accounts.removeAll(subset);
                LOGGER.debug("Added {}/{} accounts to pool ([{}/{}] out of {})",
                        success,
                        pulled,
                        authenticated.get(),
                        totalAccounts.get(),
                        totalAccounts.get() + accounts.size()
                );

                bot.saveAccounts();
            } catch (Exception ex) {
                LOGGER.error("Unexpected error", ex);
            }
        }
    }

}
