package com.bookmark.dashbook.model;

import lombok.Data;

@Data
public class GroupDetail {
    private Integer id;
    private String  title;
    private String  groupType;
    private Integer creator;
    private String  description;
    private Boolean authority;
}
