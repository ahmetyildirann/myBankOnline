package com.example.mybankonline.cardActivityFolder.service;


import com.example.mybankonline.cardFolder.service.CardService;
import com.example.mybankonline.customerFolder.service.CustomerService;
import com.example.mybankonline.transaction.dto.TransactionCardBalanceAdd;
import com.example.mybankonline.transaction.dto.TransactionCreditPayDto;
import com.example.mybankonline.transaction.dto.TransactionPaymentDto;
import com.example.mybankonline.transaction.dto.TransactionSendCardDto;
import com.example.mybankonline.transaction.service.TransactionService;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class  CardActivityService {

    private final TransactionService transactionService;

    private final CustomerService customerService;

    private final CardService cardService;

    public CardActivityService(TransactionService transactionService, CustomerService customerService, CardService cardService) {
        this.transactionService = transactionService;

        this.customerService = customerService;
        this.cardService = cardService;
    }



    public void addBalance(TransactionCardBalanceAdd dto) {
        transactionService.addBalanceCard(dto);
    }


    public void payment(TransactionPaymentDto dto) {
        transactionService.cardPayment(dto);
    }

    public void sendMoney(TransactionSendCardDto dto) throws IOException {
        transactionService.sendCard(dto);
    }


    public void creditDebtPayment(TransactionCreditPayDto dto) {
        transactionService.creditPayment(dto);
    }



}

