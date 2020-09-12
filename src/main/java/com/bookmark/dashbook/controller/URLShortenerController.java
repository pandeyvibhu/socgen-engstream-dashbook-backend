package com.bookmark.dashbook.controller;

import com.bookmark.dashbook.dto.UrlResponseDto;
import com.bookmark.dashbook.dto.UrlRequestDto;
import com.bookmark.dashbook.mapper.UrlMapper;
import com.bookmark.dashbook.service.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/url")
public class URLShortenerController {

    @Autowired
    UrlMapper urlMapper;

    @Autowired
    ShortenerService shortenerService;

    @GetMapping(value = "/get")
    public ResponseEntity<UrlResponseDto> getLongUrl(@RequestParam String shortUrl) {
        return ResponseEntity.ok(urlMapper.map(shortenerService.getUrlDetail(shortUrl)));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<UrlResponseDto> createTinyUrl(@RequestBody UrlRequestDto urlDto) {
        return ResponseEntity.ok(urlMapper.map( shortenerService.saveUrl(urlMapper.map(urlDto))));
    }

}
