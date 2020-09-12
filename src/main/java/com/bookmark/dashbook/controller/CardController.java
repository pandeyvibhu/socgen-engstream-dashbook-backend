package com.bookmark.dashbook.controller;

import com.bookmark.dashbook.dto.CardDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    public void Create(@RequestBody CardDto cardDTO) {

    }
}
