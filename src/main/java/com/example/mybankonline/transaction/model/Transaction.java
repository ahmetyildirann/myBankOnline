package com.example.mybankonline.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    private long id;

    private String cardType;

    private String cardNumber;

    private double amount;

    private String placeOfPayment;

    private String accountNumber;

    private String ibanNo;

    private String senderIban;

    private String receiverIban;


//    private String placeOfPayment;












}
