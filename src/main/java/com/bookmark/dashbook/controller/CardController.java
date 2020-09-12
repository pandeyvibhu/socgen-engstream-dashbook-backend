package com.bookmark.dashbook.controller;

import com.bookmark.dashbook.mapper.CardMapper;
import com.bookmark.dashbook.model.dto.CardDetailRequestDto;
import com.bookmark.dashbook.model.dto.CardDetailResponseDto;
import com.bookmark.dashbook.model.dto.CardDetailResponseListDto;
import com.bookmark.dashbook.model.dto.ModifiedCardRequestDto;
import com.bookmark.dashbook.service.CardService;
import com.bookmark.dashbook.service.UrlShortenerService;
import com.dashbook.bookmark.jooq.model.tables.pojos.Card;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardMapper cardMapper = Mappers.getMapper(CardMapper.class);
    @Autowired
    CardService cardService;
    @Autowired
    UrlShortenerService urlShortenerService;

    @GetMapping(value = "/groups/{groupId}")
    public ResponseEntity<CardDetailResponseListDto> getCardDetailsByGroupId(@PathVariable final int groupId) {
        CardDetailResponseListDto cardDetailResponseListDto = new CardDetailResponseListDto();
        cardDetailResponseListDto.setCardListDTO(cardService.getCardsByGroupId(groupId));
        return ResponseEntity.ok(cardDetailResponseListDto);
    }

    @GetMapping(value = "/user-created")
    public ResponseEntity<CardDetailResponseListDto> getCardsCreatedByUser() {
        CardDetailResponseListDto cardDetailResponseListDto = new CardDetailResponseListDto();
        cardDetailResponseListDto.setCardListDTO(cardService.getCardsCreatedByUser());
        return ResponseEntity.ok(cardDetailResponseListDto);
    }

    @GetMapping(value = "/favorites")
    public ResponseEntity<CardDetailResponseListDto> getFavoriteCards() {
        CardDetailResponseListDto cardDetailResponseListDto = new CardDetailResponseListDto();
        cardDetailResponseListDto.setCardListDTO(cardService.getFavoriteCards());
        return ResponseEntity.ok(cardDetailResponseListDto);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CardDetailResponseDto> createCard(@RequestBody CardDetailRequestDto cardDetailDto) {
        Card card = cardMapper.mapCardDto(cardDetailDto);
        boolean favorite = cardDetailDto.getFavorite();
        UrlDetail urlDetail = urlShortenerService.saveUrl(cardMapper.map(cardDetailDto));
        //TODO --Compute the CARD Group status
        return ResponseEntity.ok(cardMapper.mapCardDetail(cardService.createCard(card, urlDetail, favorite)));
    }

    @PostMapping(value = "/modify")
    public ResponseEntity<CardDetailResponseDto> modifyCard(@RequestBody ModifiedCardRequestDto modifyCardRequestDto) {
        Card card = cardMapper.mapModifiedCardDto(modifyCardRequestDto);
        boolean favorite = modifyCardRequestDto.getFavorite();
        return ResponseEntity.ok(cardMapper.mapCardDetail(cardService.modifyCard(card, favorite)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCard(int id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok("Card deleted");
    }

}
