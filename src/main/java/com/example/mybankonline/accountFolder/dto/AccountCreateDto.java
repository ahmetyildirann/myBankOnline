package com.example.mybankonline.accountFolder.dto;

import com.example.mybankonline.accountFolder.model.Account;
import com.example.mybankonline.customerFolder.model.Customer;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Setter
@Getter
public class AccountCreateDto {

    private String ibanNo;

    private double accountBalance;

    private String currentType;

    private String accountType;

    private LocalDate createdDate;


    private long accountId;
    public Account toAccount(){
        Customer customer = new Customer();
        customer.setId(accountId);

        return Account.builder()
                .ibanNo(this.ibanNo)
                .accountBalance(this.accountBalance)
                .currentType(this.currentType)
                .accountType(this.accountType)
                .createdDate(this.createdDate)
                .customer(customer)
                .build();
    }
}
