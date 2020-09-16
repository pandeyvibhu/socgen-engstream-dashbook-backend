package com.bookmark.dashbook.model.dto;

import lombok.Data;

@Data
public class GroupContextResponseDto {
    private Integer id;
    private String  title;
    private String  description;
    private String  groupType;
    private Boolean authority;
}
