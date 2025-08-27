package net.anurag.banking.service.impl;

import net.anurag.banking.dto.AccountDto;
import net.anurag.banking.entity.Account;
import net.anurag.banking.mapper.AccountMapper;
import net.anurag.banking.repository.AccountRepository;
import net.anurag.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService
{
    // Dependency injection
    private AccountRepository accountRepository; // this is the reference to the interface
                                                    // since we create an object of an interface

    // we got the object/implementation through dependency injection
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository)
    {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto)  // need to convert accountDto into account data entity.
    {                                        // we need a wrapper package for this conversion
        Account account = AccountMapper.mapToAccount(accountDto);
         Account savedAccount =  accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount); // so the api gets data from DTO not entity
    }

//    since maptoAccount is static we not need to create object of Accountmapper
//    AccountMapper mapper = new AccountMapper();
//    Account account = mapper.mapToAccount(accountDto);

}
