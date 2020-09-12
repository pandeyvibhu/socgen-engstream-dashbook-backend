package com.bookmark.dashbook.service;

import com.bookmark.dashbook.dao.CardDao;
import com.bookmark.dashbook.dao.UrlShortnerDao;
import com.bookmark.dashbook.mapper.CardMapper;
import com.bookmark.dashbook.model.CardDetail;
import com.bookmark.dashbook.model.dto.CardDetailResponseDto;
import com.dashbook.bookmark.jooq.model.tables.pojos.Card;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import com.dashbook.bookmark.jooq.model.tables.pojos.User;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    CardDao cardDao;

    @Autowired
    UrlShortnerDao urlShortenerDao;

    private final CardMapper cardMapper = Mappers.getMapper(CardMapper.class);

    public CardDetail createCard(Card card, UrlDetail urlDetail) {

        User user = userDetailsService.getCurrentUserDetails();
        card.setUrlDetailId(urlDetail.getId());
        card.setCreator(user.getId());
        card =  cardDao.create(card);

        CardDetail cardDetail  =  cardMapper.mapCard(card);
        cardDetail.setShortUrl(urlDetail.getShortUrl());
        cardDetail.setUrl(urlDetail.getUrl());

        return cardDetail;
    }

    public CardDetail modifyCard(Card card) {
        int urlDetailId = cardDao.findById(card.getId()).getUrlDetailId();
        UrlDetail urlDetail = urlShortenerDao.findById(urlDetailId);

        cardDao.update(card);
        CardDetail cardDetail = cardMapper.mapCard(card);
        cardDetail.setUrl(urlDetail.getUrl());
        cardDetail.setShortUrl(urlDetail.getShortUrl());

        return cardDetail;
    }

    public List<CardDetailResponseDto> getCardsByGroupId(int groupId) {
        return cardMapper.map(cardDao.getCardsByGroupId(groupId));
    }

    public List<CardDetailResponseDto> getCardsCreatedByUser() {
        User user = userDetailsService.getCurrentUserDetails();
        return cardMapper.map(cardDao.getCardsByCreatorId(user.getId()));
    }

    public List<CardDetailResponseDto> getFavoriteCards() {
        User user = userDetailsService.getCurrentUserDetails();
        return cardMapper.map(cardDao.getFavoriteCards(user.getId()));
    }
}
