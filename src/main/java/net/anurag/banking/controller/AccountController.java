package net.anurag.banking.controller;

import net.anurag.banking.dto.AccountDto;
import net.anurag.banking.dto.TransferFundDto;
import net.anurag.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController
{
    private AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    // add account REST API

    @PostMapping  // @RequestBody converts json into java object of DTO
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto)
    {
        return new ResponseEntity<>(accountService.createAccount(accountDto) , HttpStatus.CREATED);
    }
    // get Account Rest api
    @GetMapping("/{id}")  //@PathVariable to give template path var. to id
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id)
    {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    // Deposit Rest API
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id ,
                                              @RequestBody Map<String , Double> request)
    {
        Double ammount = request.get("ammount");
        AccountDto accountDto =  accountService.deposite(id , ammount);
        return  ResponseEntity.ok(accountDto);
    }

    //Withdraw rest api

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id , @RequestBody Map<String,Double> request )
    {
        double ammount = request.get("ammount");
        AccountDto accountDto = accountService.withdraw(id,ammount);
        return ResponseEntity.ok(accountDto);

    }


    // Get all accounts
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts()
    {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id)
    {
         accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted Successfully");
    }

    // build transfer rest api
    //  <String> what we return
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFund(@RequestBody  TransferFundDto transferFundDto)
    {
        accountService.transferFunds(transferFundDto);
        return ResponseEntity.ok("Transfer Sucessfull");
    }

}
