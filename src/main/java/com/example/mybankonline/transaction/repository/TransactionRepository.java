package com.example.mybankonline.transaction.repository;

import com.example.mybankonline.transaction.model.Transaction;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction,Long> {

    List<Transaction> getAllByCardNumber(String cardNumber);
}
