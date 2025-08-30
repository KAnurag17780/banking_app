package net.anurag.banking.service.impl;

import net.anurag.banking.dto.AccountDto;
import net.anurag.banking.entity.Account;
import net.anurag.banking.mapper.AccountMapper;
import net.anurag.banking.repository.AccountRepository;
import net.anurag.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

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

    @Override
    public AccountDto getAccountById(Long id) {
        // check if account exist or not
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposite(Long id, double ammount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        double total = account.getBalance() + ammount ;
        account.setBalance(total); // setBalance is a setter for balance entity(object)
        Account savedAccount =  accountRepository.save(account); // saving account to a DB
        return AccountMapper.mapToAccountDto(savedAccount);

    }

//    Withdraw can add reduce ammount
    @Override
    public AccountDto withdraw(Long id, double ammount)
    {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        if(account.getBalance() < ammount)
        {
//            this is new not new
            throw new RuntimeException("Insufficent ammount");
        }

        double total = account.getBalance() - ammount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);


        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts()
    {
       List<Account> accounts =  accountRepository.findAll();
      return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
               .collect(Collectors.toList());


    }

//    since maptoAccount is static we not need to create object of Accountmapper
//    AccountMapper mapper = new AccountMapper();
//    Account account = mapper.mapToAccount(accountDto);

}
