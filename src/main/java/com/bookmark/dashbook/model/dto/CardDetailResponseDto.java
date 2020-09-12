package com.bookmark.dashbook.model.dto;

import lombok.Data;

import java.sql.Blob;

@Data
public class CardDetailResponseDto {
    private String url;
    private String shortUrl;
    private String description;
    private String title;
    private String status;
    private Integer groupId;
    private Blob image;
}
