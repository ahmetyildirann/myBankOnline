package com.example.mybankonline.customerFolder.service;


import com.example.mybankonline.customerFolder.model.Customer;
import com.example.mybankonline.customerFolder.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {

        return customerRepository.save(customer);

    }

    public Page<Customer> getPagesCustomer(Pageable page) {

        return customerRepository.findAll(page);
    }

    public Optional<Customer> getCustomerId(long customerId) {
        return customerRepository.findById(customerId);
    }

    public void deleteCustomer(long customerId) {
      boolean exist = customerRepository.existsById(customerId);
      if (!exist){
          throw new  IllegalStateException("Customer id"+customerId+"does not exist" );
      }
      customerRepository.deleteById(customerId);
    }

    @Transactional
    public void updateCustomer(long customerId, String name, String lastname) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException(
                        "Customer with id " + customerId + "does not exist"));
        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(customer.getName(), name)) {
                 customer.setName(name);
        }
        if (lastname != null &&
                name.length() > 0 &&
                !Objects.equals(customer.getLastName(), lastname)) {
            customer.setLastName(lastname);
        }


        }
    }

