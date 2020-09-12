package com.bookmark.dashbook.model.dto;

import lombok.Data;

@Data
public class ModifiedCardRequestDto {
    private Integer id;
    private String description;
    private String title;
    private String status;
    private Integer groupId;
    private byte[]  icon;
}
