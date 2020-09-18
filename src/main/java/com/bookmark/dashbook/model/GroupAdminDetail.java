package com.bookmark.dashbook.model;

import lombok.Data;

@Data
public class GroupAdminDetail {
    private Integer id;
    private Integer groupId;
    private String username;
    private String firstname;
    private String lastname;
    private String email;

}
