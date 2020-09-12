package com.bookmark.dashbook.mapper;

import com.bookmark.dashbook.model.CardDetail;
import com.bookmark.dashbook.model.dto.CardDetailRequestDto;
import com.bookmark.dashbook.model.dto.CardDetailResponseDto;
import com.dashbook.bookmark.jooq.model.tables.pojos.Card;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import org.mapstruct.Mapper;

@Mapper
public interface CardMapper {

    UrlDetail map(CardDetailRequestDto cardDetailDto);
    Card mapCardDto(CardDetailRequestDto cardDetailDto);

    CardDetailResponseDto mapCardDetail(CardDetail cardDetail);
    CardDetail mapCard(Card card);

}
