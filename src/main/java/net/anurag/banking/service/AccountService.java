package net.anurag.banking.service;

import net.anurag.banking.dto.AccountDto;
import net.anurag.banking.dto.TransactionDto;
import net.anurag.banking.dto.TransferFundDto;

import java.util.List;

public interface AccountService
{
    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposite(Long id , double ammount );

    AccountDto withdraw(Long id , double ammount);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);

    void transferFunds(TransferFundDto transferFundDto);

    List<TransactionDto> getAccountTransactions(Long accountId);
}
