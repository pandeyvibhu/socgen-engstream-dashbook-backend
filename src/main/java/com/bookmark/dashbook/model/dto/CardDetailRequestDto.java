package com.bookmark.dashbook.model.dto;

import lombok.Data;

import java.sql.Blob;
import java.sql.Date;

@Data
public class CardDetailRequestDto {
    private String url;
    private String description;
    private String title;
    private String status;
    private Integer groupId;
    private Blob image;
}
