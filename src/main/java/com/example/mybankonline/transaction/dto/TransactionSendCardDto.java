package com.example.mybankonline.transaction.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSendCardDto {

    private String cardNumber;

    private String receiverIban;

    private double amount;
}
