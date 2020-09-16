package com.bookmark.dashbook.model.dto;

import lombok.Data;

@Data
public class CardDetailResponseDto {
    private Integer id;
    private String url;
    private String shortUrl;
    private String description;
    private String title;
    private String status;
    private Integer groupId;
    private Boolean favorite;
    private Boolean authority;
    private Boolean isCreator;
    private byte[]  icon;
}
