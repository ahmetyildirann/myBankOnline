package com.example.mybankonline.transaction.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSendDto {

    private String senderIban;
    private String receiverIban;

    private double amount;





}
