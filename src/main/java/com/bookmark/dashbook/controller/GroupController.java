package com.bookmark.dashbook.controller;

import com.bookmark.dashbook.mapper.CardMapper;
import com.bookmark.dashbook.mapper.GroupMapper;
import com.bookmark.dashbook.model.GroupDetail;
import com.bookmark.dashbook.model.dto.*;
import com.bookmark.dashbook.service.CardService;
import com.bookmark.dashbook.service.GroupService;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupAdmin;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);
    private final CardMapper cardMapper = Mappers.getMapper(CardMapper.class);

    @Autowired
    GroupService groupService;

    @Autowired
    CardService cardService;

    @GetMapping("/all")
    public ResponseEntity<GroupContextResponseListDto> getAllGroups() {
        GroupContextResponseListDto groupContextResponseListDto = new GroupContextResponseListDto();
        groupContextResponseListDto.setGroupContextResponseDtoList(groupMapper.map(groupService.findAllGroups()));
        return ResponseEntity.ok(groupContextResponseListDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupContextResponseDto> getGroupById(@PathVariable int id) {
        return ResponseEntity.ok(groupMapper.map(groupService.findGroupById(id)));
    }

    @GetMapping(value = "/{groupId}/cards")
    public ResponseEntity<CardDetailResponseListDto> getCardDetailsByGroupId(@PathVariable final int groupId) {
        CardDetailResponseListDto cardDetailResponseListDto = new CardDetailResponseListDto();
        cardDetailResponseListDto.setCardListDTO(cardMapper.map(cardService.getCardsByGroupId(groupId)));
        return ResponseEntity.ok(cardDetailResponseListDto);
    }

    @GetMapping("/{groupId}/admins")
    public ResponseEntity<GroupAdminResponseDtoList> findGroupAdmins(@PathVariable int groupId) {
        GroupAdminResponseDtoList groupAdminResponseDtoList = new GroupAdminResponseDtoList();
        groupAdminResponseDtoList.setGroupAdminResponseDtoList(groupMapper.mapAdmin(groupService.findAdminsByGroupID(groupId)));
        return ResponseEntity.ok(groupAdminResponseDtoList);
    }

    @GetMapping("/checkAdmin")
    public ResponseEntity<GroupAdminPriviligeDto> checkAdmin(@RequestParam(value = "groupId") int groupId) {
        GroupAdminPriviligeDto groupAdminPriviligeDto = new GroupAdminPriviligeDto();
        groupAdminPriviligeDto.setIsGroupAdmin(groupService.checkAdmin(groupId));
        return ResponseEntity.ok(groupAdminPriviligeDto);
    }

    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GroupContextResponseDto> saveGroup(@RequestBody GroupContextRequestDto groupContextRequestDto) {
        GroupDetail groupDetail = groupService.saveGroup(groupMapper.map(groupContextRequestDto));
        return ResponseEntity.ok(groupMapper.map(groupDetail));
    }

    @PostMapping("/admin/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GroupAdminResponseDto> addGroupAdmin(@RequestBody GroupAdminRequestDto groupAdminRequestDto) {
        GroupAdmin groupAdmin = groupService.saveAdmin(groupMapper.map(groupAdminRequestDto));
        return ResponseEntity.ok(groupMapper.map(groupAdmin));
    }

    @DeleteMapping("/delete-context/{id}")
    public ResponseEntity<GenericMessageDto> deleteGroup(@PathVariable int id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok(new GenericMessageDto("Context Group deleted successfully"));
    }

    @DeleteMapping("/delete-admin{id}")
    ResponseEntity<GenericMessageDto> deleteGroupAdmin(@PathVariable int id) {
        groupService.deleteGroupAdmin(id);
        return ResponseEntity.ok(new GenericMessageDto("Context admin deleted successfully"));
    }
}
