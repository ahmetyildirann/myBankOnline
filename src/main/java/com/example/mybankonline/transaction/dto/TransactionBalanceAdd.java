package com.example.mybankonline.transaction.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionBalanceAdd {


    private String accountNumber;
    private  double amount;
}
