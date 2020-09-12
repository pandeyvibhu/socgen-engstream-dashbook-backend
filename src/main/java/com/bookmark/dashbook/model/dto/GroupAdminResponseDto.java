package com.bookmark.dashbook.model.dto;

import lombok.Data;

@Data
public class GroupAdminResponseDto {
    int id;
    private Integer userId;
    private Integer groupId;
}
