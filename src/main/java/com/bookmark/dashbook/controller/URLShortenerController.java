package com.bookmark.dashbook.controller;

import com.bookmark.dashbook.model.dto.UrlResponseDto;
import com.bookmark.dashbook.model.dto.UrlRequestDto;
import com.bookmark.dashbook.mapper.UrlMapper;
import com.bookmark.dashbook.service.UrlShortenerService;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/tiny")
public class URLShortenerController {

    private final UrlMapper urlMapper = Mappers.getMapper(UrlMapper.class);

    @Autowired
    UrlShortenerService urlShortenerService;

    @GetMapping("/{hashValue}")
    public void getLongUrl(HttpServletResponse httpServletResponse, @PathVariable("hashValue") String hashValue) throws IOException {
        httpServletResponse.sendRedirect(urlShortenerService.getUrlDetail(hashValue).getUrl());
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UrlResponseDto> createTinyUrl(@RequestBody UrlRequestDto urlDto) {
        UrlDetail urlDetail = urlShortenerService.saveUrl(urlMapper.map(urlDto));
        return ResponseEntity.ok(urlMapper.map(urlDetail));
    }
}
