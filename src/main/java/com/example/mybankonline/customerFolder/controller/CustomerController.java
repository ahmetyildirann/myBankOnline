package com.example.mybankonline.customerFolder.controller;

import com.example.mybankonline.customerFolder.dto.CustomerCreateDto;
import com.example.mybankonline.customerFolder.dto.CustomerDto;
import com.example.mybankonline.customerFolder.model.Customer;
import com.example.mybankonline.customerFolder.service.CustomerService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("customer")
    public CustomerDto create(@RequestBody CustomerCreateDto dto){

//          return customerService.saveCustomer(dto.toCustomer());

        Customer customer = customerService.saveCustomer(dto.toCustomer());
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .identity(customer.getIdentity())
                .password(customer.getPassword())
                .build();
    }

    @GetMapping("customers")
    public Page<CustomerDto> listCustomers(Pageable page){
        return customerService.getPagesCustomer(page)
                .map(customer -> CustomerDto.builder()
                        .id(customer.getId())
                        .name(customer.getName())
                        .lastName(customer.getLastName())
                        .identity(customer.getIdentity())
                        .password(customer.getPassword())
                        .build());
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(
            @PathVariable("customerId") long customerId){
        customerService.deleteCustomer(customerId);
    }

    @PutMapping(path ="{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Long customerId,
            @RequestParam (required = false) String name,
            @RequestParam (required = false) String lastname) {
        customerService.updateCustomer(customerId, name, lastname);
    }
}
