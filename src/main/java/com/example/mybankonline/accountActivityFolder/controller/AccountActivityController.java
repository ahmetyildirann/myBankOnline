package com.example.mybankonline.accountActivityFolder.controller;



import com.example.mybankonline.accountActivityFolder.service.AccountActivityService;
import com.example.mybankonline.accountFolder.service.AccountService;
import com.example.mybankonline.interest.InterestDto;
import com.example.mybankonline.transaction.dto.TransactionBalanceAdd;
import com.example.mybankonline.transaction.dto.TransactionSendDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/")
public class AccountActivityController {

  private final AccountActivityService accountActivityService;
  private final AccountService accountService;

  public AccountActivityController(AccountActivityService accountActivityService, AccountService accountService) {
    this.accountActivityService = accountActivityService;
    this.accountService = accountService;
  }

  @PostMapping("addBalance")
  @ResponseStatus(HttpStatus.CREATED)
  public void createAddBalance(@RequestBody TransactionBalanceAdd dto){
      accountActivityService.addBalance(dto);
       }

    @PostMapping("sendMoneyFromDrawingtoDeposit")
    @ResponseStatus(HttpStatus.CREATED)
    public void createsendMoney(@RequestBody TransactionSendDto dto) throws IOException, InterruptedException {
      accountActivityService.sendMoney(dto);
    }

    @PostMapping("interestDeposit")
    @ResponseStatus(HttpStatus.CREATED)
    public void calculateDeposit(@RequestBody InterestDto dto){
      accountActivityService.calculationDeposit(dto);
    }





}
