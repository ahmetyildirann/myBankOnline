package com.example.mybankonline.transaction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionCreditPayDto {

    private String placeOfPayment;
    private String cardNumber;
    private double amount;
    private String accountNumber;

}
