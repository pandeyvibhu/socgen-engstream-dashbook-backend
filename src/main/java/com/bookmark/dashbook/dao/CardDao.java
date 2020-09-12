package com.bookmark.dashbook.dao;

import com.dashbook.bookmark.jooq.model.Tables;
import com.dashbook.bookmark.jooq.model.tables.pojos.Card;
import com.dashbook.bookmark.jooq.model.tables.records.CardRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

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
}
