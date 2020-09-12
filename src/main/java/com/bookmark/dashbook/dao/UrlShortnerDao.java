package com.bookmark.dashbook.dao;

import com.dashbook.bookmark.jooq.model.Tables;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import com.dashbook.bookmark.jooq.model.tables.pojos.User;
import com.dashbook.bookmark.jooq.model.tables.records.UrlDetailRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com.dashbook.bookmark.jooq.model.tables.UrlDetail.URL_DETAIL;

@Repository
public class UrlShortnerDao extends DAOImpl<UrlDetailRecord, UrlDetail, Integer> {

    @Autowired
    DSLContext context;

    public UrlShortnerDao(DSLContext context) {
        super(URL_DETAIL, UrlDetail.class, context.configuration());
        this.context = context;
    }

    public UrlDetail insertShortUrl(UrlDetail url, String shortUrl) {
        return context.insertInto(Tables.URL_DETAIL,
                Tables.URL_DETAIL.URL,Tables.URL_DETAIL.SHORT_URL,
                Tables.URL_DETAIL.EXPIRATION_TIME, Tables.URL_DETAIL.CREATION_TIME)
                .values(url.getUrl(), shortUrl, url.getExpirationTime(), LocalDateTime.now())
                .returning(URL_DETAIL.ID, URL_DETAIL.URL, URL_DETAIL.SHORT_URL, URL_DETAIL.EXPIRATION_TIME)
                .fetchOne()
                .into(UrlDetail.class);
    }

    public UrlDetail getUrlDetail(String shortUrl){
        return context
                .selectFrom(Tables.URL_DETAIL)
                .where(Tables.URL_DETAIL.SHORT_URL.eq(shortUrl))
                .fetchOneInto(UrlDetail.class);
    }

    @Override
    public Integer getId(UrlDetail urlDetail) {
        return urlDetail.getId();
    }
}
