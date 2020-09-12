package com.bookmark.dashbook.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UrlRequestDto {
    private String url;
    private LocalDateTime expirationTime;
}
