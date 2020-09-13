package com.bookmark.dashbook.service;

import com.bookmark.dashbook.dao.GroupAdminDao;
import com.bookmark.dashbook.dao.GroupDao;
import com.bookmark.dashbook.model.dto.GroupAdminResponseDto;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupAdmin;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupContext;
import com.dashbook.bookmark.jooq.model.tables.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    GroupDao groupDao;

    @Autowired
    GroupAdminDao groupAdminDao;

    public List<GroupContext> findAllGroups() {
        return groupDao.findAll();
    }

    public GroupContext findGroupById(int groupId) {
        return groupDao.findById(groupId);
    }

    public List<GroupAdmin> findAdminsByGroupID(int groupId) {
        return groupDao.findAdminsByGroupId(groupId);
    }

    public GroupContext saveGroup(GroupContext group) {
        User user = userDetailsService.getCurrentUserDetails();
        group.setCreator(user.getId());
        group = groupDao.upsert(group);

        GroupAdmin groupAdmin = new GroupAdmin();
        groupAdmin.setGroupId(group.getId());
        groupAdmin.setUserId(user.getId());
        groupAdminDao.upsert(groupAdmin);
        return group;
    }

    public GroupAdmin saveAdmin(GroupAdmin groupAdmin) {
        return groupAdminDao.upsert(groupAdmin);
    }

    public Boolean checkAdmin(int groupId) {
        User user = userDetailsService.getCurrentUserDetails();
        return groupDao.checkGroupAdmin(user.getId(), groupId);
    }

    public void deleteGroup(int groupId) {
        groupAdminDao.deleteByGroupId(groupId);
        groupDao.deleteById(groupId);
    }

    public void deleteGroupAdmin(int id) {
        groupAdminDao.deleteById(id);
    }

}

