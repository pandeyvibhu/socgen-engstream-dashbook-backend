package com.bookmark.dashbook.controller;

import com.bookmark.dashbook.mapper.GroupMapper;
import com.bookmark.dashbook.model.dto.*;
import com.bookmark.dashbook.service.GroupService;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupAdmin;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupContext;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);
    @Autowired
    GroupService groupService;

    @GetMapping("")
    public ResponseEntity<GroupContextResponseListDto> getAllGroups() {
        GroupContextResponseListDto groupContextResponseListDto = new GroupContextResponseListDto();
        groupContextResponseListDto.setGroupContextResponseDtoList(groupMapper.map(groupService.findAllGroups()));
        return ResponseEntity.ok(groupContextResponseListDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupContextResponseDto> getGroupById(@PathVariable int id) {
        return ResponseEntity.ok(groupMapper.map(groupService.findGroupById(id)));
    }

    @GetMapping("checkAdmin")
    public ResponseEntity<GroupAdminPriviligeDto> checkAdmin(@RequestParam(value = "groupId", required = true) int groupId) {
        GroupAdminPriviligeDto groupAdminPriviligeDto = new GroupAdminPriviligeDto();
        groupAdminPriviligeDto.setIsGroupAdmin(groupService.checkAdmin(groupId));
        return ResponseEntity.ok(groupAdminPriviligeDto);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<GroupContextResponseDto> saveGroup(@RequestBody GroupContextRequestDto groupContextRequestDto) {
        GroupContext groupContext = groupService.saveGroup(groupMapper.map(groupContextRequestDto));
        return ResponseEntity.ok(groupMapper.map(groupContext));
    }

    @PostMapping("/admin/add")
    public ResponseEntity<GroupAdminResponseDto> addGroupAdmin(@RequestBody GroupAdminRequestDto groupAdminRequestDto) {
        GroupAdmin groupAdmin = groupService.saveAdmin(groupMapper.map(groupAdminRequestDto));
        return ResponseEntity.ok(groupMapper.map(groupAdmin));
    }

    @DeleteMapping("/delete-context{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable int id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok("Card deleted");
    }

    @DeleteMapping("/delete-admin{id}")
    public ResponseEntity<String> deleteGroupAdmin(@PathVariable int id) {
        groupService.deleteGroupAdmin(id);
        return ResponseEntity.ok("Card deleted");
    }
}
