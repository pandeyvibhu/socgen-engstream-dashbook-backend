package com.bookmark.dashbook.model.dto;

import lombok.Data;

@Data
public class CardDetailRequestDto {
    private String url;
    private String description;
    private String title;
    private String status;
    private Integer groupId;
    private byte[]  icon;
}
