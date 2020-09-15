package com.bookmark.dashbook.dao;

import com.bookmark.dashbook.model.CardDetail;
import com.dashbook.bookmark.jooq.model.Tables;
import com.dashbook.bookmark.jooq.model.tables.pojos.Card;
import com.dashbook.bookmark.jooq.model.tables.pojos.Favorites;
import com.dashbook.bookmark.jooq.model.tables.records.CardRecord;
import com.dashbook.bookmark.jooq.model.tables.records.FavoritesRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.dashbook.bookmark.jooq.model.Tables.FAVORITES;
import static com.dashbook.bookmark.jooq.model.tables.Card.CARD;

@Repository
public class CardDao extends DAOImpl<CardRecord, Card, Integer> {

    @Autowired
    DSLContext context;

    public CardDao(DSLContext context) {
        super(CARD, Card.class, context.configuration());
        this.context = context;
    }

    public List<CardDetail> findAllCards() {
        return context.select(
                Tables.CARD.ID, Tables.CARD.DESCRIPTION, Tables.CARD.TITLE, Tables.CARD.ICON, Tables.CARD.STATUS,
                Tables.CARD.GROUP_ID, Tables.URL_DETAIL.SHORT_URL, Tables.URL_DETAIL.URL)
                .from(Tables.CARD)
                .join(Tables.URL_DETAIL).on(Tables.CARD.URL_DETAIL_ID.eq(Tables.URL_DETAIL.ID))
                .fetchInto(CardDetail.class);
    }

    public List<CardDetail> getCardsByGroupId(int groupId) {
        return context.select(
                Tables.CARD.ID, Tables.CARD.DESCRIPTION, Tables.CARD.TITLE, Tables.CARD.ICON, Tables.CARD.STATUS,
                Tables.CARD.GROUP_ID, Tables.URL_DETAIL.SHORT_URL, Tables.URL_DETAIL.URL)
                .from(Tables.CARD)
                .join(Tables.URL_DETAIL).on(Tables.CARD.URL_DETAIL_ID.eq(Tables.URL_DETAIL.ID))
                .where(Tables.CARD.GROUP_ID.eq(groupId))
                .fetchInto(CardDetail.class);
    }

    public List<CardDetail> getCardsByCreatorId(int creatorId) {
        return context.select(
                Tables.CARD.ID, Tables.CARD.DESCRIPTION, Tables.CARD.TITLE, Tables.CARD.ICON, Tables.CARD.STATUS,
                Tables.CARD.GROUP_ID, Tables.URL_DETAIL.SHORT_URL, Tables.URL_DETAIL.URL)
                .from(Tables.CARD)
                .join(Tables.URL_DETAIL).on(Tables.CARD.URL_DETAIL_ID.eq(Tables.URL_DETAIL.ID))
                .where(Tables.CARD.CREATOR.eq(creatorId))
                .fetchInto(CardDetail.class);
    }

    public List<CardDetail> getFavoriteCards(int userId) {
        return context.select(
                Tables.CARD.ID, Tables.CARD.DESCRIPTION, Tables.CARD.TITLE, Tables.CARD.ICON, Tables.CARD.STATUS,
                Tables.CARD.GROUP_ID, Tables.URL_DETAIL.SHORT_URL, Tables.URL_DETAIL.URL)
                .from(Tables.CARD)
                .join(Tables.URL_DETAIL).on(Tables.CARD.URL_DETAIL_ID.eq(Tables.URL_DETAIL.ID))
                .join(FAVORITES).on(Tables.CARD.ID.eq(FAVORITES.CARD_ID).and(Tables.URL_DETAIL.ID.eq(FAVORITES.CARD_ID)))
                .where(FAVORITES.USER_ID.eq(userId))
                .fetchInto(CardDetail.class);
    }

    public Card create(Card card) {
        return context.insertInto(Tables.CARD,
                Tables.CARD.CREATOR, Tables.CARD.TITLE, Tables.CARD.DESCRIPTION, Tables.CARD.STATUS,
                Tables.CARD.URL_DETAIL_ID, Tables.CARD.CREATION_DATE, Tables.CARD.GROUP_ID, Tables.CARD.ICON)
                .values(card.getCreator(), card.getTitle(), card.getDescription(), card.getStatus(),
                        card.getUrlDetailId(), LocalDateTime.now(), card.getGroupId(), card.getIcon())
                .returning(CARD.ID, CARD.URL_DETAIL_ID, CARD.GROUP_ID, CARD.TITLE, CARD.DESCRIPTION, CARD.ICON, CARD.STATUS)
                .fetchOne()
                .into(Card.class);
    }

    public void deleteCard(int cardId) {
        Card card = findById(cardId);
        if (card != null) {
            context.deleteFrom(FAVORITES)
                    .where(FAVORITES.CARD_ID.eq(cardId))
                    .execute();
            context.deleteFrom(Tables.URL_DETAIL)
                    .where(Tables.URL_DETAIL.ID.eq(card.getUrlDetailId()))
                    .execute();
            delete(card);
        }
    }

    @Override
    public Integer getId(Card card) {
        return card.getId();
    }

    //Queries on FAVORITE table for user favorite cards
    public boolean isFavoriteCard(int userId, int cardId) {
        return context.fetchExists(
                context.selectFrom(FAVORITES)
                        .where(FAVORITES.USER_ID.eq(userId))
                        .and(FAVORITES.CARD_ID.eq(cardId)));
    }

    public void upsertFavorite(Favorites favorites) {
        FavoritesRecord favoritesRecord = context.newRecord(FAVORITES, favorites);

        context.insertInto(FAVORITES)
                .set(favoritesRecord)
                .onConflict(FAVORITES.CARD_ID, FAVORITES.USER_ID)
                .doUpdate();
    }

    public void deleteFavorite(int userId, int cardId) {
        context.deleteFrom(FAVORITES)
                .where(FAVORITES.USER_ID.eq(userId).and(FAVORITES.CARD_ID.eq(cardId)))
                .execute();
    }

    public boolean isCreatedByUser(int userId) {
        return context.fetchExists(
                context.selectFrom(Tables.CARD)
                        .where(CARD.CREATOR.eq(userId)));
    }
}
