package com.bookmark.dashbook.model.dto;

import lombok.Data;

@Data
public class GroupAdminResponseDto {
    private Integer id;
    private Integer groupId;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
}
