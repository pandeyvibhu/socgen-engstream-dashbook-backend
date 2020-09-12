package com.bookmark.dashbook.service;

import com.bookmark.dashbook.dao.CardDao;
import com.bookmark.dashbook.mapper.CardMapper;
import com.bookmark.dashbook.model.CardDetail;
import com.dashbook.bookmark.jooq.model.tables.pojos.Card;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import com.dashbook.bookmark.jooq.model.tables.pojos.User;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    CardDao cardDao;

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
}
