package com.example.mybankonline.cardFolder.model;


import com.example.mybankonline.customerFolder.model.Customer;
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
public class Card {

    @Id
    @GeneratedValue()
    private long id;

    @Column(unique = true)
    private String cardNumber;

    private String expiredDate;
    private String securityNumber;
    private String cardType;
    private double cardLimit;
    private double currentLimit;
    private String ibanNo;

    private boolean isUsable;





    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;



}
