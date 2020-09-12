package com.bookmark.dashbook.controller;

import com.bookmark.dashbook.mapper.CardMapper;
import com.bookmark.dashbook.model.dto.CardDetailRequestDto;
import com.bookmark.dashbook.model.dto.CardDetailResponseDto;
import com.bookmark.dashbook.service.CardService;
import com.bookmark.dashbook.service.UrlShortenerService;
import com.dashbook.bookmark.jooq.model.tables.pojos.Card;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    UrlShortenerService urlShortenerService;

    private final CardMapper cardMapper = Mappers.getMapper(CardMapper.class);

    @PostMapping(value = "/create")
    public ResponseEntity<CardDetailResponseDto> createCard(@RequestBody CardDetailRequestDto cardDetailDto) {
        Card card = cardMapper.mapCardDto(cardDetailDto);
        UrlDetail urlDetail = urlShortenerService.saveUrl(cardMapper.map(cardDetailDto));
        //TODO --Compute the CARD Group status
        return ResponseEntity.ok(cardMapper.mapCardDetail(cardService.createCard(card, urlDetail)));
    }
}
