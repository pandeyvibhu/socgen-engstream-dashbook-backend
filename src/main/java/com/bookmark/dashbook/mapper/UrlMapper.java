package com.bookmark.dashbook.mapper;

import com.bookmark.dashbook.model.dto.UrlRequestDto;
import com.bookmark.dashbook.model.dto.UrlResponseDto;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import org.mapstruct.Mapper;

@Mapper
public interface UrlMapper {
    UrlResponseDto map(UrlDetail url);

    UrlDetail map(UrlRequestDto dto);
}
