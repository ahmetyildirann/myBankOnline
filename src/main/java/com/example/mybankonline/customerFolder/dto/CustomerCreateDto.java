package com.example.mybankonline.customerFolder.dto;

import com.example.mybankonline.customerFolder.model.Customer;
import lombok.Setter;


@Setter
public class CustomerCreateDto {

    private String name;
    private String lastName;


    private long identity;

    private String password;



    public Customer toCustomer(){

        return Customer.builder()
                .name(this.name)
                .lastName(this.lastName)
                .identity(this.identity)
                .password(this.password)
                .build();

    }
}
