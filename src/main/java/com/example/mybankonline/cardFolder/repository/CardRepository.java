package com.example.mybankonline.cardFolder.repository;

import com.example.mybankonline.cardFolder.model.Card;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CardRepository extends PagingAndSortingRepository<Card,Long> {

    Card findCardByCardNumber(String cardNumber);

    Card findCardByCustomerId(long id);

}
