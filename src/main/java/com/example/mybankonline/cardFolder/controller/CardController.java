package com.example.mybankonline.cardFolder.controller;


import com.example.mybankonline.cardFolder.cardDto.CardCreateDto;
import com.example.mybankonline.cardFolder.cardDto.CardDto;
import com.example.mybankonline.cardFolder.model.Card;
import com.example.mybankonline.cardFolder.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("card")
    public CardDto create(@RequestBody CardCreateDto dto){

        Card card = cardService.saveCard(dto.toCard());
      return   CardDto.builder()
              .id(card.getId())
              .cardType(card.getCardType())
              .cardLimit(card.getCardLimit())
              .cardNumber(card.getCardNumber())
              .currentLimit(card.getCurrentLimit())
              .expiredDate(card.getExpiredDate())
              .securityNumber(card.getSecurityNumber())
              .isUsable(card.isUsable())
              .ibanNo(card.getIbanNo())
              .build();
    }

    @GetMapping("cards")
    public Page<Card> listCard(Pageable pageable){
        return cardService.getPageOfCards(pageable);
    }

    @PutMapping(path ="{cardId}")
    public void updateCard(
            @PathVariable("cardId") Long cardId,
            @RequestParam (required = false) String cardNumber,
            @RequestParam (required = false) String cardType,
            @RequestParam (required = false) double cardLimit ) {
        cardService.updateCard(cardId, cardNumber, cardType, String.valueOf(cardLimit));

    }

    @DeleteMapping(path = "{cardId}")
    public void deletecard(
            @PathVariable("cardId") long cardId){
        cardService.deleteCustomer(cardId);
    }

}
