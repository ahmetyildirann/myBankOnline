package com.example.mybankonline.cardFolder.service;

import com.example.mybankonline.accountFolder.model.Account;
import com.example.mybankonline.accountFolder.service.AccountService;
import com.example.mybankonline.cardFolder.model.Card;
import com.example.mybankonline.cardFolder.repository.CardRepository;
import com.example.mybankonline.customerFolder.model.Customer;
import com.example.mybankonline.customerFolder.service.CustomerService;
import com.example.mybankonline.exeption.NotFoundExeptions;
import com.example.mybankonline.transaction.model.Transaction;
import com.example.mybankonline.transaction.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CardService {

    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;
    private final CardRepository cardRepository;

    private final AccountService accountService;



    public CardService(TransactionRepository transactionRepository, CustomerService customerService, CardRepository cardRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.customerService = customerService;
        this.cardRepository = cardRepository;
        this.accountService = accountService;
    }



    public Card saveCard(Card card) {


            if (!(card.getCardType().equals("Credit Card") ||card.getCardType().equals("Debit Card"))){

                log.info("card type must be Credit Card, Debit Card");
                return null;
            }


        if (card.getCardType().equals("Credit Card")){

            Optional<Customer> customerOptional= customerService.getCustomerId(card.getCustomer().getId());
            Customer customer = customerOptional.orElseThrow(()-> new NotFoundExeptions("Customer id : "+ card.getCustomer().getId()+ " is nor found"));

            card.setCardLimit(5000);
            card.getCustomer().setId(customer.getId());
            return cardRepository.save(card);
        }else {
            Optional<Customer> customerOptional= customerService.getCustomerId(card.getCustomer().getId());
            Customer customer = customerOptional.orElseThrow(()-> new NotFoundExeptions("Customer id : "+ card.getCustomer().getId()+ " is nor found"));


            Optional<Account> accountOptional = accountService.getIban(card.getIbanNo());
            Account account =accountOptional.orElseThrow(()->new NotFoundExeptions("Account id not found" ));

            if (card.getCustomer().getId() == account.getCustomer().getId()){

                if (account.getAccountBalance() >50){
                    account.setAccountBalance(account.getAccountBalance()-50);
                }

            }else System.out.println("Customer is different");


            card.getCustomer().setId(customer.getId());

            return cardRepository.save(card);

        }




    }

    public Page<Card> getPageOfCards(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    public Optional<Card> getById(long id) {
        return cardRepository.findById(id);
    }



    public Optional<Card> getCardNumber(String cardNumber){
        return Optional.ofNullable(cardRepository.findCardByCardNumber(cardNumber));
    }


    public String debtInquiry(String cardNumber) {
        Optional<Card> cardOptional = Optional.ofNullable(cardRepository.findCardByCardNumber(cardNumber));
        Card card = cardOptional.orElseThrow(()->new NotFoundExeptions("Card number not found"));
        double debt = card.getCardLimit()-card.getCurrentLimit();
        return "The Credit Card Debit is : "+debt;
    }

    public List<Transaction> findByTransactionCardTypeAndCardNo(String cardNumber) {

        Card creditCard = cardRepository.findCardByCardNumber(cardNumber);
        if(creditCard==null){

            return null;
        }
        return transactionRepository.getAllByCardNumber(cardNumber);
    }
    @Transactional
    public void updateCard(Long cardId, String cardNumber, String cardType, String cardLimit) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalStateException(
                        "Card with id " + cardId + "does not exist"));

        if (cardNumber != null &&
                cardNumber.length() > 0 &&
                !Objects.equals(card.getCardNumber(), cardNumber)) {
            card.setCardNumber(cardNumber);
        }

        if (cardType != null &&
                cardType.length() > 0 &&
                !Objects.equals(card.getCardType(), cardType)) {
            card.setCardType(cardType);
        }

        if (cardLimit != null &&
                cardLimit.length() > 0 &&
                !Objects.equals(card.getCardLimit(), cardLimit)) {
            card.setCardLimit(Double.parseDouble(cardLimit));
        }
    }


    public void deleteCustomer(long cardId) {

        Optional<Card> cardOptional =  getById(cardId);
        Card card = cardOptional.orElseThrow(()-> new NotFoundExeptions("id nont found"));
        if (card.getCardLimit() != card.getCurrentLimit()){

            log.info("You can't delete the card because you have card debt");
        }else {
            boolean exist = cardRepository.existsById(cardId);
            if (!exist){
                throw new NotFoundExeptions("Customer id"+cardId+"does not exist");
            }
        }

    }
}
