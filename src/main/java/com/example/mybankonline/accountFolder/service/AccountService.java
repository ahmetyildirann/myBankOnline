package com.example.mybankonline.accountFolder.service;


import com.example.mybankonline.accountFolder.model.Account;
import com.example.mybankonline.accountFolder.repository.AccountRepository;
import com.example.mybankonline.customerFolder.model.Customer;
import com.example.mybankonline.customerFolder.service.CustomerService;
import com.example.mybankonline.exeption.NotFoundExeptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public AccountService(AccountRepository accountRepository, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    public Account saveAccount(Account account) {
//        boolean isTrue =true;
//        while (isTrue){
//            if (!(account.getCurrentType().equals("USD") || account.getCurrentType().equals("EUR") || account.getCurrentType().equals("TRY") || account.getAccountType().equals("Deposit Account") || account.getAccountType().equals("Drawing Account"))) {
//        System.out.println("Currency type must be USD,EUR ,TRY  and Account type must be Deposit Account, Drawing Account ");
//           continue;
//        isTrue =false;
//     }
//

        Optional<Customer> customerOptional= customerService.getCustomerId(account.getCustomer().getId());
        Customer customer = customerOptional.orElseThrow(()-> new NotFoundExeptions("Customer id : "+ account.getCustomer().getId()+ " is not found"));


        customer.getId();
        account.getCustomer().setId(customer.getId());
        return accountRepository.save(account);


    }

    public Page<Account> getPageAcoount(Pageable page) {
        return accountRepository.findAll(page);
    }

    public Optional<Account> getById(long id) {
        return accountRepository.findById(id);
    }
    public Optional<Account> getIban(String accountNumber){
        return Optional.ofNullable(accountRepository.findByIbanNo(accountNumber));
    }

    public Optional<Account> getCustomerId(long id){
        return Optional.ofNullable(accountRepository.findAccountByCustomer_Id(id));
    }


    public void deleteCustomer(long accountId) {
        boolean exist = accountRepository.existsById(accountId);
        if (!exist){
            throw new  IllegalStateException("Account id"+accountId+"does not exist" );
        }
        accountRepository.deleteById(accountId);
    }
    @Transactional
    public void updateAccount(long accountId, String ibanNo, String accountType, String currencyType) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException(
                        "Account with id " + accountId + "does not exist"));
        if (ibanNo != null &&
                ibanNo.length() > 0 &&
                !Objects.equals(account.getIbanNo(), ibanNo)) {
            account.setIbanNo(ibanNo);
        }
        if (accountType != null &&
                accountType.length() > 0 &&
                !Objects.equals(account.getAccountType(), accountType)) {
            account.setAccountType(accountType);
        }
        if (currencyType != null &&
                currencyType.length() > 0 &&
                !Objects.equals(account.getCurrentType(), currencyType)) {
            account.setCurrentType(currencyType);
        }

    }
}
