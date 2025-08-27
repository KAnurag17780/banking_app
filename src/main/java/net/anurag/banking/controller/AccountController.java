package net.anurag.banking.controller;

import net.anurag.banking.dto.AccountDto;
import net.anurag.banking.repository.AccountRepository;
import net.anurag.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
