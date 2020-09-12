package com.bookmark.dashbook.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CardDetailResponseListDto {
    private List<CardDetailResponseDto> cardListDTO;
}
