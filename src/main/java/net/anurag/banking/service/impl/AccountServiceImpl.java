package net.anurag.banking.service.impl;

import net.anurag.banking.dto.AccountDto;
import net.anurag.banking.dto.TransactionDto;
import net.anurag.banking.dto.TransferFundDto;
import net.anurag.banking.entity.Account;
import net.anurag.banking.entity.Transaction;
import net.anurag.banking.exception.AccountException;
import net.anurag.banking.mapper.AccountMapper;
import net.anurag.banking.repository.AccountRepository;
import net.anurag.banking.repository.TransactionRepository;
import net.anurag.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class AccountServiceImpl implements AccountService
{
    // Dependency injection
    private AccountRepository accountRepository; // this is the reference to the interface
                                                    // since we create an object of an interface
    private TransactionRepository transactionRepository;

    private static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
    private static final String TRANSACTION_TYPE_WITHDRAW = "WITHDRAW";
    private static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";

    // we got the object/implementation through dependency injection
    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository ,
                              TransactionRepository transactionRepository)
    {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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
                .orElseThrow(() -> new AccountException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposite(Long id, double ammount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist"));

        double total = account.getBalance() + ammount ;
        account.setBalance(total); // setBalance is a setter for balance entity(object)
        Account savedAccount =  accountRepository.save(account); // saving account to a DB


        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getId());
        transaction.setAmmount(ammount);
        transaction.setTransactionType(TRANSACTION_TYPE_DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);  // this shold be last statment always
    }

//    Withdraw can add reduce ammount
    @Override
    public AccountDto withdraw(Long id, double ammount)
    {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist"));

        if(account.getBalance() < ammount)
        {
//            this is new not new
            throw new RuntimeException("Insufficent ammount");
        }

        double total = account.getBalance() - ammount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccountId(id);
        transaction.setAmmount(ammount);
        transaction.setTransactionType(TRANSACTION_TYPE_WITHDRAW);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
        return AccountMapper.mapToAccountDto(savedAccount);

    }

    @Override
    public List<AccountDto> getAllAccounts()
    {
       List<Account> accounts =  accountRepository.findAll();
      return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
               .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountException("Account does not exist"));

        accountRepository.deleteById(id);

    }

    @Override
    public void transferFunds(TransferFundDto transferFundDto) {
        // Retrieve the account from which we send the account
        Account fromAccount = accountRepository
                .findById(transferFundDto.fromAccountId())
                .orElseThrow(() -> new AccountException("Account does not exist"));

        // Retrive the account to which we send the ammount
         Account toAccount = accountRepository
                .findById(transferFundDto.toAccountId())
                .orElseThrow(() -> new AccountException("Account does not exist"));

         if(fromAccount.getBalance() < transferFundDto.ammount() )
         {
            throw new RuntimeException("Insufficient Amount");
         }

         // Debit the amount fromAccount object
            fromAccount.setBalance(fromAccount.getBalance() - transferFundDto.ammount());
            // credit the amount toAccount object
            toAccount.setBalance(toAccount.getBalance() + transferFundDto.ammount());

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            Transaction transaction = new Transaction();
            transaction.setAccountId(transferFundDto.fromAccountId());
            transaction.setAmmount(transferFundDto.ammount());
            transaction.setTransactionType(TRANSACTION_TYPE_TRANSFER);
            transaction.setTimestamp(LocalDateTime.now());

            transactionRepository.save(transaction);

    }

    @Override
    public List<TransactionDto> getAccountTransactions(Long accountId) {
       List<Transaction> transactions = transactionRepository
               .findByAccountIdOrderByTimestampDesc(accountId);

        return transactions.stream()
                .map((transaction) -> convertEntityToDto(transaction))
                .collect(Collectors.toList());



    }

    // convert transaction entity to transaction Dto
    private TransactionDto convertEntityToDto(Transaction transaction)
    {
        return new TransactionDto(
            transaction.getId(),
            transaction.getAccountId(),
            transaction.getAmmount(),
            transaction.getTransactionType(),
            transaction.getTimestamp()
        );
    }

//    since maptoAccount is static we not need to create object of Accountmapper
//    AccountMapper mapper = new AccountMapper();
//    Account account = mapper.mapToAccount(accountDto);

}
