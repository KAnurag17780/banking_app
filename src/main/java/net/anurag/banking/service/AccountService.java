package net.anurag.banking.service;

import net.anurag.banking.dto.AccountDto;
import net.anurag.banking.entity.Account;

public interface AccountService
{
    AccountDto createAccount(AccountDto accountDto);
}
