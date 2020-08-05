package me.hfox.craftbot.auth;

import java.util.List;

public class AccountsFileDto {

    private List<AccountDto> accounts;

    public List<AccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDto> accounts) {
        this.accounts = accounts;
    }

}
