package com.bookmark.dashbook.controller;

import com.bookmark.dashbook.model.dto.UrlResponseDto;
import com.bookmark.dashbook.model.dto.UrlRequestDto;
import com.bookmark.dashbook.mapper.UrlMapper;
import com.bookmark.dashbook.service.UrlShortenerService;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class URLShortenerController {

    private final UrlMapper urlMapper = Mappers.getMapper(UrlMapper.class);

    @Autowired
    UrlShortenerService urlShortenerService;

    @GetMapping(value = "/get")
    public ResponseEntity<UrlResponseDto> getLongUrl(@RequestParam String shortUrl) {
        return ResponseEntity.ok(urlMapper.map(urlShortenerService.getUrlDetail(shortUrl)));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UrlResponseDto> createTinyUrl(@RequestBody UrlRequestDto urlDto) {
        UrlDetail urlDetail = urlShortenerService.saveUrl(urlMapper.map(urlDto));
        return ResponseEntity.ok(urlMapper.map(urlDetail));

    }

}
