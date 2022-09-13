package com.example.mybankonline.cardFolder.cardDto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class CardDto {
    private long id;
    private String cardNumber;
    private String cardType;
    private double cardLimit;
    private double currentLimit;
    private boolean isUsable;
    private String securityNumber;
    private String expiredDate;
    private String ibanNo;
//    private  customerDto

}
