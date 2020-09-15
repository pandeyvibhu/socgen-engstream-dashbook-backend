package com.bookmark.dashbook.model;

import lombok.Data;

@Data
public class CardDetail {
    //url details field
    private String url;
    private String shortUrl;
    //Card fields
    private Integer id;
    private String description;
    private String title;
    private String status;
    private Integer groupId;
    private byte[]  icon;
    //favorites table field
    private Boolean favorite;
    //Authority
    private Boolean authority;
}
