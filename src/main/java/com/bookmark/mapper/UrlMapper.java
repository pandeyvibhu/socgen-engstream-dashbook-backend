package com.bookmark.mapper;

import com.bookmark.dto.UrlRequestDto;
import com.bookmark.dto.UrlResponseDto;
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
