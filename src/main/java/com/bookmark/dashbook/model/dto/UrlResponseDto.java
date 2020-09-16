package com.bookmark.dashbook.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UrlResponseDto {
    private Integer id;
    private String url;
    private String shortUrl;
    private LocalDateTime creationTime;
    private LocalDateTime expirationTime;
}

