package com.example.mybankonline.cardActivityFolder.controller;


import com.example.mybankonline.cardActivityFolder.service.CardActivityService;
import com.example.mybankonline.cardFolder.service.CardService;

import com.example.mybankonline.transaction.dto.TransactionCardBalanceAdd;
import com.example.mybankonline.transaction.dto.TransactionCreditPayDto;
import com.example.mybankonline.transaction.dto.TransactionPaymentDto;
import com.example.mybankonline.transaction.dto.TransactionSendCardDto;
import com.example.mybankonline.transaction.model.Transaction;
import com.example.mybankonline.transaction.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/")
public class CardActivityController {



    private final CardActivityService cardActivityService;
    private final CardService cardService;



    public CardActivityController(CardActivityService cardActivityService, TransactionService transactionService1, CardService cardService) {

        this.cardActivityService = cardActivityService;
        this.cardService = cardService;

    }


    @PostMapping("addBalancefromAtm")
    public void createBallence(@RequestBody TransactionCardBalanceAdd dto){
        cardActivityService.addBalance(dto);
    }

    @PostMapping("payment")
    public void  cardPayment(@RequestBody TransactionPaymentDto dto){
        cardActivityService.payment(dto);
    }

    @PostMapping("sendMoneyFromCardToIban")
    public void sendMoney(@RequestBody TransactionSendCardDto dto) throws IOException {
        cardActivityService.sendMoney(dto);
    }

    @PostMapping("creditDebtPayment")
    public void creditDebtPayment(@RequestBody TransactionCreditPayDto dto){
        cardActivityService.creditDebtPayment(dto);
    }

    @GetMapping("{cardNumber}")
    public List<Transaction> findTransaction(@PathVariable String cardNumber){
        return cardService.findByTransactionCardTypeAndCardNo(cardNumber);
    }

    @GetMapping("cardNumberDebt")
    public String debtInquiry( String cardNumber){

        return cardService.debtInquiry(cardNumber);
    }

}
