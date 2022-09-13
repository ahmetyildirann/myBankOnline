package com.example.mybankonline.accountActivityFolder.service;


import com.example.mybankonline.accountFolder.model.Account;
import com.example.mybankonline.accountFolder.service.AccountService;
import com.example.mybankonline.customerFolder.service.CustomerService;
import com.example.mybankonline.interest.InterestDto;
import com.example.mybankonline.transaction.dto.TransactionBalanceAdd;
import com.example.mybankonline.transaction.dto.TransactionSendDto;
import com.example.mybankonline.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;


@Service
@Slf4j
public class AccountActivityService {

    private final TransactionService transactionService;
    

    private final CustomerService customerService;
    private final AccountService accountService;

    public AccountActivityService(TransactionService transactionService,  CustomerService customerService, AccountService accountService) {
        this.transactionService = transactionService;

        this.customerService = customerService;
        this.accountService = accountService;
    }






    public void addBalance(TransactionBalanceAdd dto) {

        transactionService.addBalance(dto);

    }

   public  void sendMoney(TransactionSendDto dto ) throws IOException, InterruptedException {
        transactionService.send(dto);
    }

    public void calculationDeposit(InterestDto dto) {

        Optional<Account> accountOptional = accountService.getIban(dto.getAccountNumber());
        Account account = accountOptional.orElseThrow(() -> new IllegalArgumentException(dto.getAccountNumber() + "Not found exeption"));

        if (account.getAccountType().equals("Deposit Account")){
            if(dto.getDayNumber()<0 || dto.getDayNumber()>365)
              ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Day must be between 0 and 365");

            if(dto.getAccountBallace()<1000){
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Starter balance have minimum 1000 ");
            }
            double rate = dto.getInterestRate();
            double interestMoneyCalculate=(dto.getDayNumber()/365)*dto.getAccountBallace()*(rate/100);
            double taxRate=0.1;

            double interestYield = (interestMoneyCalculate)-(taxRate*interestMoneyCalculate);

           account.setInterestYield(account.getAccountBalance()+interestYield);
           account.setCreatedDate(LocalDate.now());
           accountService.saveAccount(account);

        }
        else {
         log.info("This Acoount is not Deposit");
        }


    }
}
