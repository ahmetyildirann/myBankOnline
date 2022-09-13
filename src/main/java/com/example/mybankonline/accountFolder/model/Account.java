package com.example.mybankonline.accountFolder.model;

import com.example.mybankonline.customerFolder.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private long id;


    @Column(unique = true)
    private String ibanNo;

    private double accountBalance;


    private String currentType;


    private String accountType;

    private LocalDate createdDate;

    private double interestYield;




    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    private Customer customer;


}
