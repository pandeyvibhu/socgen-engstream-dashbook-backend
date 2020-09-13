package com.bookmark.dashbook.mapper;

import com.bookmark.dashbook.model.dto.GroupAdminRequestDto;
import com.bookmark.dashbook.model.dto.GroupAdminResponseDto;
import com.bookmark.dashbook.model.dto.GroupContextRequestDto;
import com.bookmark.dashbook.model.dto.GroupContextResponseDto;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupAdmin;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupContext;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {
    GroupContext map(GroupContextRequestDto groupContextRequestDto);

    GroupContextResponseDto map(GroupContext groupContext);

    GroupAdmin map(GroupAdminRequestDto groupAdminRequestDto);

    GroupAdminResponseDto map(GroupAdmin groupAdmin);

    List<GroupContextResponseDto> map(List<GroupContext> groupContextList);

    List<GroupAdminResponseDto> mapAdmin(List<GroupAdmin> groupAdmins);

}
