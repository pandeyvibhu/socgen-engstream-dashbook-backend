package com.bookmark.dashbook.service;

import com.bookmark.dashbook.dao.UrlShortnerDao;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import com.dashbook.bookmark.jooq.model.tables.pojos.User;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ShortenerService {

    @Autowired
    UrlShortnerDao urlShortnerDao;

    @Autowired
    MyUserDetailsService userDetailsService;

    public UrlDetail getUrlDetail(String shortUrl) {
        return urlShortnerDao.getUrlDetail(shortUrl);
    }

    public UrlDetail saveUrl(UrlDetail urlDetail) {
        User user = userDetailsService.getCurrentUserDetails();
        String shortUrl = Hashing.murmur3_32()
                .hashString(urlDetail.getUrl().toString()+user.toString(), StandardCharsets.UTF_8)
                .toString();

        return urlShortnerDao.insertShortUrl(urlDetail, shortUrl);

    }
}
