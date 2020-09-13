package com.bookmark.dashbook.service;

import com.bookmark.dashbook.dao.CardDao;
import com.bookmark.dashbook.dao.UrlShortnerDao;
import com.bookmark.dashbook.mapper.CardMapper;
import com.bookmark.dashbook.model.CardDetail;
import com.dashbook.bookmark.jooq.model.tables.pojos.Card;
import com.dashbook.bookmark.jooq.model.tables.pojos.Favorites;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import com.dashbook.bookmark.jooq.model.tables.pojos.User;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final CardMapper cardMapper = Mappers.getMapper(CardMapper.class);
    @Autowired
    MyUserDetailsService userDetailsService;
    @Autowired
    CardDao cardDao;
    @Autowired
    UrlShortnerDao urlShortenerDao;

    public List<CardDetail> findAll() {
        return (appendFavoriteInfoToCardDetail(cardDao.findAllCards()));
    }

    public List<CardDetail> getCardsByGroupId(int groupId) {
        return (appendFavoriteInfoToCardDetail(cardDao.getCardsByGroupId(groupId)));
    }

    public List<CardDetail> getCardsCreatedByUser() {
        User user = userDetailsService.getCurrentUserDetails();
        return appendFavoriteInfoToCardDetail(cardDao.getCardsByCreatorId(user.getId()));
    }

    public List<CardDetail> getFavoriteCards() {
        User user = userDetailsService.getCurrentUserDetails();
        return appendFavoriteInfoToCardDetail(cardDao.getFavoriteCards(user.getId()));
    }

    private List<CardDetail> appendFavoriteInfoToCardDetail(List<CardDetail> cardDetailList) {
        cardDetailList.stream().forEach(cardDetail -> {
            User user = userDetailsService.getCurrentUserDetails();
            cardDetail.setFavorite(cardDao.isFavoriteCard(user.getId(), cardDetail.getId()));
        });
        return cardDetailList;
    }

    public CardDetail createCard(Card card, UrlDetail urlDetail, boolean favorite) {
        User user = userDetailsService.getCurrentUserDetails();
        card.setUrlDetailId(urlDetail.getId());
        card.setCreator(user.getId());
        card = cardDao.create(card);

        CardDetail cardDetail = cardMapper.mapCard(card);
        cardDetail.setShortUrl(urlDetail.getShortUrl());
        cardDetail.setUrl(urlDetail.getUrl());

        if (favorite) {
            markFavorite(user.getId(), card.getId());
        }

        return cardDetail;
    }

    public CardDetail modifyCard(Card card, boolean favorite) {
        int urlDetailId = cardDao.findById(card.getId()).getUrlDetailId();
        User user = userDetailsService.getCurrentUserDetails();
        UrlDetail urlDetail = urlShortenerDao.findById(urlDetailId);

        cardDao.update(card);
        CardDetail cardDetail = cardMapper.mapCard(card);
        cardDetail.setUrl(urlDetail.getUrl());
        cardDetail.setShortUrl(urlDetail.getShortUrl());

        if (favorite) {
            markFavorite(user.getId(), card.getId());
        } else {
            cardDao.deleteFavorite(user.getId(), card.getId());
        }
        cardDetail.setFavorite(favorite);
        return cardDetail;
    }

    private void markFavorite(int userId, int cardId) {
        Favorites favorites = new Favorites();
        favorites.setUserId(userId);
        favorites.setCardId(cardId);
        cardDao.upsertFavorite(favorites);
    }

    public void deleteCard(int cardId) {
        cardDao.deleteCard(cardId);
    }

}
