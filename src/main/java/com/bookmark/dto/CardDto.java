package com.bookmark.dto;

import lombok.Data;

import java.sql.Blob;
import java.sql.Date;

@Data
public class CardDto {
    //url details field
    private int urlDetailId;
    private String url;
    private String shortUrl;
    private Date creationTime;
    private Date expirationTime;
    //Card fields
    private String description;
    private String title;
    private String status;
    private int group_id;
    private Blob image;
}
