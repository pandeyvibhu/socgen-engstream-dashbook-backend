package com.bookmark.controller;

import com.bookmark.dto.UrlRequestDto;
import com.bookmark.dto.UrlResponseDto;
import com.bookmark.mapper.UrlMapper;
import com.bookmark.service.ShortenerService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
