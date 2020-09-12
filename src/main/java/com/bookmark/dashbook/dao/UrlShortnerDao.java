package com.bookmark.dashbook.dao;

import com.dashbook.bookmark.jooq.model.Tables;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UrlShortnerDao {

    @Autowired
    DSLContext context;

    public UrlDetail insertShortUrl(UrlDetail url, String shortUrl) {
        return context.insertInto(Tables.URL_DETAIL,
                Tables.URL_DETAIL.URL,Tables.URL_DETAIL.SHORT_URL,
                Tables.URL_DETAIL.EXPIRATION_TIME, Tables.URL_DETAIL.CREATION_TIME)
                .values(url.getUrl(), shortUrl, url.getExpirationTime(), url.getCreationTime())
                .returning(Tables.URL_DETAIL.URL,Tables.URL_DETAIL.SHORT_URL)
                .fetchOne()
                .into(UrlDetail.class);
    }

    public UrlDetail getUrlDetail(String shortUrl){
        return context
                .selectFrom(Tables.URL_DETAIL)
                .where(Tables.URL_DETAIL.SHORT_URL.eq(shortUrl))
                .fetchOneInto(UrlDetail.class);
    }

}
