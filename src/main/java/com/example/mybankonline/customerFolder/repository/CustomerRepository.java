package com.example.mybankonline.customerFolder.repository;

import com.example.mybankonline.customerFolder.model.Customer;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long> {


}
