package com.bookmark.dashbook.mapper;

import com.bookmark.dashbook.model.GroupAdminDetail;
import com.bookmark.dashbook.model.GroupDetail;
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

    GroupAdminResponseDto map(GroupAdminDetail groupAdminDetail);

    GroupDetail map(GroupContext groupContext);

    GroupContextResponseDto map(GroupDetail groupDetail);

    GroupAdmin map(GroupAdminRequestDto groupAdminRequestDto);

    GroupAdminResponseDto map(GroupAdmin groupAdmin);

    List<GroupDetail> mapGroup(List<GroupContext> groupContext);

    List<GroupContextResponseDto> map(List<GroupDetail> groupDetailList);

    List<GroupAdminResponseDto> mapAdmins(List<GroupAdminDetail> groupAdminDetailList);
}
