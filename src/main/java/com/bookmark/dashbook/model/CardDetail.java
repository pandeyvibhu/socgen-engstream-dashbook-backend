package com.bookmark.dashbook.model;

import lombok.Data;

@Data
public class CardDetail {
    //url details field
    private String url;
    private String shortUrl;
    //Card fields
    private String description;
    private String title;
    private String status;
    private Integer groupId;
    private byte[]  icon;
}
