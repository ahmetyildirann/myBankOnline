package com.example.mybankonline.accountFolder.repository;

import com.example.mybankonline.accountFolder.model.Account;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account,Long> {

    Account findByIbanNo(String ibanNo);

    Account findAccountByCustomer_Id(Long id);



}
