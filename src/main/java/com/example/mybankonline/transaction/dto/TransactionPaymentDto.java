package com.example.mybankonline.transaction.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionPaymentDto {

    private String cardNumber;
    private String ibanNo;
    private  double amount;

}
