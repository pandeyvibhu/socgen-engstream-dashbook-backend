package com.bookmark.dashbook.dao;

import com.dashbook.bookmark.jooq.model.tables.pojos.GroupAdmin;
import com.dashbook.bookmark.jooq.model.tables.records.GroupAdminRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.dashbook.bookmark.jooq.model.Tables.GROUP_ADMIN;

@Repository
public class GroupAdminDao extends DAOImpl<GroupAdminRecord, GroupAdmin, Integer> {

    @Autowired
    DSLContext context;

    public GroupAdminDao(DSLContext context) {
        super(GROUP_ADMIN, GroupAdmin.class, context.configuration());
        this.context = context;
    }

    @Override
    public Integer getId(GroupAdmin groupAdmin) {
        return groupAdmin.getId();
    }

    public GroupAdmin upsert(GroupAdmin groupAdmin) {

        GroupAdminRecord groupAdminRecord = context.newRecord(GROUP_ADMIN, groupAdmin);

        return context.insertInto(GROUP_ADMIN)
                .set(groupAdminRecord)
                .onConflict(GROUP_ADMIN.USER_ID, GROUP_ADMIN.GROUP_ID)
                .doUpdate()
                .set(groupAdminRecord)
                .returning(GROUP_ADMIN.ID, GROUP_ADMIN.USER_ID, GROUP_ADMIN.GROUP_ID)
                .fetchOne()
                .into(GroupAdmin.class);
    }

    public void deleteByGroupId(int groupId) {
        context.delete(GROUP_ADMIN)
                .where(GROUP_ADMIN.GROUP_ID.eq(groupId))
                .execute();
    }
}
