package com.bookmark.dashbook.mapper;

import com.bookmark.dashbook.dto.UrlResponseDto;
import com.bookmark.dashbook.dto.UrlRequestDto;
import com.dashbook.bookmark.jooq.model.tables.pojos.UrlDetail;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UrlMapper {

    UrlResponseDto map(UrlDetail url);
    UrlDetail map(UrlResponseDto dto);
    UrlDetail map(UrlRequestDto dto);

    List<UrlResponseDto> map(List<UrlDetail> urls);
}
