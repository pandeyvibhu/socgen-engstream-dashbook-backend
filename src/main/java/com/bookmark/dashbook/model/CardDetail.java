package com.bookmark.dashbook.model;

import lombok.Data;

import java.sql.Blob;
import java.sql.Date;

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
    private Blob image;
}
