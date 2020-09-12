package com.bookmark.dashbook.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UrlResponseDto {
    private int id;
    private String url;
    private String shortUrl;
    private LocalDateTime creationTime;
    private LocalDateTime expirationTime;
}

