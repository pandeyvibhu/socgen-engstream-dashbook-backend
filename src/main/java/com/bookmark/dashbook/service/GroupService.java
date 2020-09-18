package com.bookmark.dashbook.service;

import com.bookmark.dashbook.dao.CardDao;
import com.bookmark.dashbook.dao.GroupAdminDao;
import com.bookmark.dashbook.dao.GroupDao;
import com.bookmark.dashbook.mapper.GroupMapper;
import com.bookmark.dashbook.model.GroupDetail;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupAdmin;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupContext;
import com.dashbook.bookmark.jooq.model.tables.pojos.User;
import org.mapstruct.factory.Mappers;
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
    CardDao cardDao;

    @Autowired
    GroupAdminDao groupAdminDao;

    private final GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);

    public List<GroupDetail> findUserGroups() {
        return enrichAuthorityInfo(groupDao.findUserGroups());
    }

    public GroupDetail findGroupById(int groupId) {
        return enrichAuthorityInfo(groupDao.findById(groupId));
    }

    public List<GroupAdmin> findAdminsByGroupID(int groupId) {
        return groupDao.findAdminsByGroupId(groupId);
    }

    public GroupDetail saveGroup(GroupContext group) {
        boolean ifGroupCreation = (group.getId()==null);
        GroupDetail groupDetail;
        User user = userDetailsService.getCurrentUserDetails();

        if(ifGroupCreation) {
            group.setCreator(user.getId());
            group = groupDao.upsert(group);
        } else {
            groupDao.update(group);
        }

        //Update the Group Creator as Group Admin
        if(ifGroupCreation) {
            GroupAdmin groupAdmin = new GroupAdmin();
            groupAdmin.setGroupId(group.getId());
            groupAdmin.setUserId(user.getId());
            groupAdminDao.upsert(groupAdmin);
        }

        groupDetail = groupMapper.map(group);
        groupDetail.setAuthority(true);
        return groupDetail;
    }

    public GroupAdmin saveAdmin(GroupAdmin groupAdmin) {
        return groupAdminDao.upsert(groupAdmin);
    }

    GroupDetail enrichAuthorityInfo(GroupContext groupContext){
        GroupDetail groupDetail = groupMapper.map(groupContext);
        groupDetail.setAuthority(checkAdmin(groupContext.getId()));
        return groupDetail;
    }

    List<GroupDetail> enrichAuthorityInfo(List<GroupContext> groupContextList){
        List<GroupDetail> groupDetailList= groupMapper.mapGroup(groupContextList);
        groupDetailList.forEach(groupDetail -> {
            groupDetail.setAuthority(checkAdmin(groupDetail.getId()));
        });
        return groupDetailList;
    }

    public Boolean checkAdmin(int groupId) {
        User user = userDetailsService.getCurrentUserDetails();
        return groupDao.checkGroupAdmin(user.getId(), groupId);
    }

    public void deleteGroup(int groupId) {
        cardDao.updateCardsGroupId(groupId);
        groupAdminDao.deleteByGroupId(groupId);
        groupDao.deleteById(groupId);
    }

    public void deleteGroupAdmin(int id) {
        groupAdminDao.deleteById(id);
    }

}

