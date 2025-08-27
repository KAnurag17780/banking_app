package net.anurag.banking.mapper;

import net.anurag.banking.dto.AccountDto;
import net.anurag.banking.entity.Account;

public class AccountMapper
{
    // Static hone ka matlab: class ke object ki zarurat nahi, directly call kar sakte ho:
    // input AccountDto   , output Account(Entity)
    public static Account mapToAccount(AccountDto accountDto)
    {
        Account account = new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance()
        );
        return account;

    }

    // input Account(Entity) , output AccountDto
    public static AccountDto mapToAccountDto(Account account)
    {
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accountDto;
    }


}
