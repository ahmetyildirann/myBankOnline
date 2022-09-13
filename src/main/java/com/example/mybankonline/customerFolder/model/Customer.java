package com.example.mybankonline.customerFolder.model;

import com.example.mybankonline.cardFolder.model.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String lastName;

    private long identity;

    private String password;







      @OneToMany(mappedBy = "customer")
      private List<Card> cardList;











}
