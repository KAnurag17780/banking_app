package net.anurag.banking.service;

import net.anurag.banking.dto.AccountDto;
import net.anurag.banking.entity.Account;

public interface AccountService
{
    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposite(Long id , double ammount );
}
