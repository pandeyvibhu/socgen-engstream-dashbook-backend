package com.bookmark.dashbook.model.dto;

import lombok.Data;

@Data
public class GroupContextRequestDto {
    private String id;
    private String title;
    private String description;
    private String groupType;
}
