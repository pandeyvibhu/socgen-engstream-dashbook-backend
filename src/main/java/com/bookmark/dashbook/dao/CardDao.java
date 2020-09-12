package com.bookmark.dashbook.dao;

import com.bookmark.dashbook.model.CardDetail;
import com.dashbook.bookmark.jooq.model.Tables;
import com.dashbook.bookmark.jooq.model.tables.pojos.Card;
import com.dashbook.bookmark.jooq.model.tables.records.CardRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.dashbook.bookmark.jooq.model.tables.Card.CARD;

@Repository
public class CardDao  extends DAOImpl<CardRecord, Card, Integer> {

    @Autowired
    DSLContext context;

    public CardDao(DSLContext context) {
        super(CARD, Card.class, context.configuration());
        this.context = context;
    }

    public Card create(Card card) {
        return context.insertInto(Tables.CARD,
                Tables.CARD.CREATOR,Tables.CARD.TITLE, Tables.CARD.DESCRIPTION, Tables.CARD.STATUS,
                Tables.CARD.URL_DETAIL_ID, Tables.CARD.CREATION_DATE, Tables.CARD.GROUP_ID, Tables.CARD.ICON)
                .values(card.getCreator(), card.getTitle(), card.getDescription(), card.getStatus(),
                        card.getUrlDetailId(), LocalDateTime.now(), card.getGroupId(), card.getIcon())
                .returning(CARD.ID, CARD.URL_DETAIL_ID, CARD.GROUP_ID, CARD.TITLE, CARD.DESCRIPTION, CARD.ICON, CARD.STATUS )
                .fetchOne()
                .into(Card.class);
    }

    @Override
    public Integer getId(Card card) {
        return null;
    }

    public List<CardDetail> getCardsByGroupId(int groupId) {
        return context.select(
                Tables.CARD.DESCRIPTION, Tables.CARD.TITLE, Tables.CARD.ICON, Tables.CARD.STATUS,
                Tables.URL_DETAIL.SHORT_URL, Tables.URL_DETAIL.URL)
                .from(Tables.CARD)
                .join(Tables.URL_DETAIL).on(Tables.CARD.URL_DETAIL_ID.eq(Tables.URL_DETAIL.ID))
                .where(Tables.CARD.GROUP_ID.eq(groupId))
                .fetchInto(CardDetail.class);
    }

    public List<CardDetail> getCardsByCreatorId(int creatorId) {
        return context.select(
                Tables.CARD.DESCRIPTION, Tables.CARD.TITLE, Tables.CARD.ICON, Tables.CARD.STATUS,
                Tables.URL_DETAIL.SHORT_URL, Tables.URL_DETAIL.URL)
                .from(Tables.CARD)
                .join(Tables.URL_DETAIL).on(Tables.CARD.URL_DETAIL_ID.eq(Tables.URL_DETAIL.ID))
                .where(Tables.CARD.CREATOR.eq(creatorId))
                .fetchInto(CardDetail.class);
    }

    public List<CardDetail> getFavoriteCards(int userId) {
        return context.select(
                Tables.CARD.DESCRIPTION, Tables.CARD.TITLE, Tables.CARD.ICON, Tables.CARD.STATUS,
                Tables.URL_DETAIL.SHORT_URL, Tables.URL_DETAIL.URL)
                .from(Tables.CARD)
                .join(Tables.URL_DETAIL).on(Tables.CARD.URL_DETAIL_ID.eq(Tables.URL_DETAIL.ID))
                .join(Tables.FAVORITES).on(Tables.CARD.ID.eq(Tables.FAVORITES.CARD_ID).and(Tables.URL_DETAIL.ID.eq(Tables.FAVORITES.CARD_ID)))
                .where(Tables.FAVORITES.USER_ID.eq(userId))
                .fetchInto(CardDetail.class);
    }
}
