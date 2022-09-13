package com.example.mybankonline.cardFolder.cardDto;

import com.example.mybankonline.cardFolder.model.Card;
import com.example.mybankonline.customerFolder.model.Customer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardCreateDto {

    private String cardNumber;

    private String ibanNo;
    private String cardType;
    private boolean isUsable;
    private String securityNumber;
    private String expiredDate;
    private double cardLimit;
    private double currentLimit;

//    private Customer customer;

  private long customerId;
//private long customerId;
    public Card toCard(){
    Customer customer = new Customer();
     customer.setId(customerId);

        return Card.builder()
                .cardNumber(this.cardNumber)
                .cardType(this.cardType)
                .isUsable(this.isUsable)
                .securityNumber(this.securityNumber)
                .expiredDate(this.expiredDate)
                .cardLimit(this.cardLimit)
                .currentLimit(this.currentLimit)
                .ibanNo(this.ibanNo)
                .customer(customer)
                .build();
    }
}
//                .