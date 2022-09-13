package com.example.mybankonline.customerFolder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {

    private long id;
    private String name;
    private String lastName;
    private long identity;
    private String password;
}
