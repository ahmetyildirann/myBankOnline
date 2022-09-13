package com.example.mybankonline.interest;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class InterestDto {

    private int dayNumber;

    private String accountNumber;

    private LocalDate createdDate;

//    private LocalDate endDate;
//
//    private boolean isActive;
//
//    private int remainingDay;
//
    private double accountBallace;

//    private LocalDate cancelDate;

    private double interestRate;

    private double interestYield;





}
