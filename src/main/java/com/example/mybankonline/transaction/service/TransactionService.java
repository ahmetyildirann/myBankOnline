package com.example.mybankonline.transaction.service;

import com.example.mybankonline.accountFolder.model.Account;
import com.example.mybankonline.accountFolder.service.AccountService;
import com.example.mybankonline.cardFolder.model.Card;
import com.example.mybankonline.cardFolder.service.CardService;
import com.example.mybankonline.currencyFolder.CurrencyService;
import com.example.mybankonline.transaction.dto.*;
import com.example.mybankonline.transaction.model.Transaction;
import com.example.mybankonline.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class TransactionService {



    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    private final CardService cardService;

    private final CurrencyService currencyService;




    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, CardService cardService, CurrencyService currencyService) {
        this.transactionRepository = transactionRepository;

        this.accountService = accountService;

        this.cardService = cardService;


        this.currencyService = currencyService;
    }





    public void addBalance(TransactionBalanceAdd dto) {

        Optional<Account> accountOptional = accountService.getIban(dto.getAccountNumber());


        Account account = accountOptional.orElseThrow(() -> new IllegalArgumentException(dto.getAccountNumber() + "Not found exeption"));



            transactionRepository.save(
                    Transaction.builder()
                            .accountNumber(dto.getAccountNumber())
                            .amount(account.getAccountBalance() + dto.getAmount())
                            .build()
            );

            account.setAccountBalance(account.getAccountBalance() + dto.getAmount());
            accountService.saveAccount(account);

    }


    @Transactional
    public void send(TransactionSendDto dto) throws IOException {


        Optional<Account> sendeerOptional = accountService.getIban(dto.getSenderIban());

        Account sendeer = sendeerOptional.orElseThrow(() -> new IllegalArgumentException(dto.getSenderIban() + " Not found exeption"));

        Optional<Account> receiveerOptional = accountService.getIban(dto.getReceiverIban());

        Account receiveer = receiveerOptional.orElseThrow(() -> new IllegalArgumentException(dto.getReceiverIban() + " Not found exeption"));

        transactionRepository.save(
                Transaction.builder()
                        .senderIban(dto.getSenderIban())
                        .receiverIban(dto.getReceiverIban())
                        .amount(dto.getAmount())
                        .build()
        );

        if (dto.getAmount()<sendeer.getAccountBalance()) {


                sendeer.setAccountBalance(sendeer.getAccountBalance() - dto.getAmount());
                accountService.saveAccount(sendeer);
                receiveer.setAccountBalance(receiveer.getAccountBalance() + currencyService.setAccountBalancer(dto.getAmount(), sendeer, receiveer));
                accountService.saveAccount(receiveer);


        }else {
            log.info("Account Balance "+sendeer.getAccountBalance()+" must be bigger than amaunt " + dto.getAmount());
        }

    }
    @Transactional
    public void addBalanceCard(TransactionCardBalanceAdd dto) {

        Optional<Card> cardOptional = cardService.getCardNumber(dto.getCardNumber());

        Card card = cardOptional.orElseThrow(() -> new IllegalArgumentException(dto.getCardNumber() + "Not found exeption"));

        Optional<Account> accountOptional = accountService.getIban(dto.getAccountNumber());

        Account account = accountOptional.orElseThrow(() -> new IllegalArgumentException(dto.getAccountNumber() + "Not found exeption"));


if (card.getCustomer().getId() == account.getCustomer().getId()) {

    transactionRepository.save(
            Transaction.builder()
                    .cardNumber(dto.getCardNumber())
                    .accountNumber(dto.getAccountNumber())
                    .amount(account.getAccountBalance() + dto.getAmount())
                    .build()
    );
    account.setAccountBalance(account.getAccountBalance() + dto.getAmount());
    accountService.saveAccount(account);
}
else log.info("not same customer Id");

        }
    @Transactional
    public void cardPayment(TransactionPaymentDto dto) {
        Optional<Card> cardOptional = cardService.getCardNumber(dto.getCardNumber());

        Card card = cardOptional.orElseThrow(() -> new IllegalArgumentException(dto.getCardNumber() + "Not found exeption"));




        if (card.getCardType().equals("Credit Card")){
            transactionRepository.save(
                    Transaction.builder()
                            .cardNumber(dto.getCardNumber())
                            .amount(card.getCardLimit()-dto.getAmount())
                            .build()
            );
            card.setCurrentLimit(card.getCurrentLimit()-dto.getAmount());
            cardService.saveCard(card);

        }else {


            Optional<Account> accountOptional = accountService.getIban(card.getIbanNo());

            Account account = accountOptional.orElseThrow(() -> new IllegalArgumentException(card.getCustomer().getId() + "Not found exeption"));




            if (card.getCustomer().getId() == account.getCustomer().getId()) {

                transactionRepository.save(
                        Transaction.builder()
                                .cardNumber(dto.getCardNumber())
                                .ibanNo(dto.getIbanNo())
                                .amount(account.getAccountBalance() + dto.getAmount())
                                .build()
                );

                if (dto.getAmount()<account.getAccountBalance()) {
                    account.setAccountBalance(account.getAccountBalance() - dto.getAmount());
                    accountService.saveAccount(account);
                }
            } log.info("not same customer Id");


        }




    }
    @Transactional
    public void sendCard(TransactionSendCardDto dto) throws IOException {


        Optional<Card> cardOptional = cardService.getCardNumber(dto.getCardNumber());

        Card card = cardOptional.orElseThrow(() -> new IllegalArgumentException(dto.getCardNumber() + "Not found exeption"));



        Optional<Account> accountOptional = accountService.getCustomerId(card.getCustomer().getId());

        Account account = accountOptional.orElseThrow(() -> new IllegalArgumentException(card.getCustomer().getId() + "Not found exeption"));



        Optional<Account> receiveerOptional = accountService.getIban(dto.getReceiverIban());

        Account receiveer = receiveerOptional.orElseThrow(() -> new IllegalArgumentException(dto.getReceiverIban() + "Not found exeption"));

        transactionRepository.save(
                Transaction.builder()
                        .senderIban(dto.getCardNumber())
                        .receiverIban(dto.getReceiverIban())
                        .amount(dto.getAmount())
                        .build()
        );

        if (account.getAccountType().equals("Deposit Account")){
           log.info("Can not transfer From Deposit Account");

        }else {

            if (dto.getAmount()<account.getAccountBalance()) {

                account.setAccountBalance(account.getAccountBalance() - dto.getAmount());
                accountService.saveAccount(account);
                receiveer.setAccountBalance(receiveer.getAccountBalance() + currencyService.setAccountBalancer(dto.getAmount(), account, receiveer));
                accountService.saveAccount(receiveer);
            }else
                log.info("Account Balance must be bigger than amaunt");
        }




    }

    @Transactional
    public void creditPayment(TransactionCreditPayDto dto) {

        Optional<Card> cardOptional = cardService.getCardNumber(dto.getCardNumber());

        Card card = cardOptional.orElseThrow(() -> new IllegalArgumentException(dto.getCardNumber() + "Not found exeption"));

        if (dto.getPlaceOfPayment().equals("fromAtm")){


            transactionRepository.save(
                    Transaction.builder()
                            .cardNumber(dto.getCardNumber())
                            .placeOfPayment(dto.getPlaceOfPayment())
                            .amount(dto.getAmount())
                            .build()
            );
            card.setCurrentLimit(card.getCurrentLimit()+dto.getAmount());
            cardService.saveCard(card);

        }if (dto.getPlaceOfPayment().equals("fromAccount")){

            Optional<Account> accountOptional = accountService.getIban(dto.getAccountNumber());

            Account account = accountOptional.orElseThrow(() -> new IllegalArgumentException(dto.getAccountNumber() + "Not found exeption"));


            if (card.getCustomer().getId() == account.getCustomer().getId()) {

                transactionRepository.save(
                        Transaction.builder()
                                .cardNumber(dto.getCardNumber())
                                .accountNumber(dto.getAccountNumber())
                                .amount(account.getAccountBalance() - dto.getAmount())
                                .build()
                );
                card.setCurrentLimit(card.getCurrentLimit()+dto.getAmount());
                cardService.saveCard(card);
                account.setAccountBalance(account.getAccountBalance() - dto.getAmount());
                accountService.saveAccount(account);
        }
    }







    }
}
















