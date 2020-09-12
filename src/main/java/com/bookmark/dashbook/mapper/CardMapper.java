package com.bookmark.dashbook.mapper;

import com.bookmark.dashbook.model.CardDetail;
import com.bookmark.dashbook.model.dto.CardDetailRequestDto;
import com.bookmark.dashbook.model.dto.CardDetailResponseDto;
import com.bookmark.dashbook.model.dto.ModifiedCardRequestDto;
import com.dashbook.bookmark.jooq.model.tables.pojos.Card;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CardMapper {

    CardDetail mapCard(Card card);

    UrlDetail map(CardDetailRequestDto cardDetailDto);

    Card mapCardDto(CardDetailRequestDto cardDetailDto);

    Card mapModifiedCardDto(ModifiedCardRequestDto modifiedCardRequestDto);

    CardDetailResponseDto mapCardDetail(CardDetail cardDetail);

    List<CardDetailResponseDto> map(List<CardDetail> cardDetailList);

}
