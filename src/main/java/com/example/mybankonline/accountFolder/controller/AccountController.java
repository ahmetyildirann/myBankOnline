package com.example.mybankonline.accountFolder.controller;

import com.example.mybankonline.accountFolder.dto.AccountCreateDto;
import com.example.mybankonline.accountFolder.dto.AccountDto;
import com.example.mybankonline.accountFolder.model.Account;
import com.example.mybankonline.accountFolder.service.AccountService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("account")
    public AccountDto create(@RequestBody AccountCreateDto dto ){

        Account account = accountService.saveAccount(dto.toAccount());

        return AccountDto.builder()
                .id(account.getId())
                .accountType(account.getAccountType())
                .accountBalance(account.getAccountBalance())
                .ibanNo(account.getIbanNo())
                .currentType(account.getCurrentType())
                .build();



    }

    @GetMapping("accounts")

    public Page<AccountDto> listAccount(Pageable page){
        return accountService.getPageAcoount(page)
                .map(account -> AccountDto.builder()
                        .id(account.getId())
                        .ibanNo(account.getIbanNo())
                        .accountBalance(account.getAccountBalance())
                        .currentType(account.getCurrentType())
                        .accountType(account.getAccountType())
                        .build());
    }

    @DeleteMapping(path = "{accountId}")
    public void deleteCustomer(
            @PathVariable("accountId") long accountId) {
        accountService.deleteCustomer(accountId);
    }

    @PutMapping(path ="{accountId}")
    public void updateAccount(
            @PathVariable("accountId") long accountId,
            @RequestParam (required = false) String ibanNo,
            @RequestParam (required = false) String accountType,
            @RequestParam (required = false) String currencyType) {
        accountService.updateAccount(accountId, ibanNo, accountType,currencyType);
    }


}
