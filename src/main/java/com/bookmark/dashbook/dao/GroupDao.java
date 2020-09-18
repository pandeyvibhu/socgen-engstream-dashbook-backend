package com.bookmark.dashbook.dao;

import com.bookmark.dashbook.model.GroupAdminDetail;
import com.bookmark.dashbook.service.CardService;
import com.dashbook.bookmark.jooq.model.Tables;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupAdmin;
import com.dashbook.bookmark.jooq.model.tables.pojos.GroupContext;
import com.dashbook.bookmark.jooq.model.tables.records.GroupContextRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import static com.dashbook.bookmark.jooq.model.Tables.GROUP_ADMIN;
import static com.dashbook.bookmark.jooq.model.tables.GroupContext.GROUP_CONTEXT;

@Repository
public class GroupDao extends DAOImpl<GroupContextRecord, GroupContext, Integer> {

    @Autowired
    DSLContext context;

    public GroupDao(DSLContext context) {
        super(GROUP_CONTEXT, GroupContext.class, context.configuration());
        this.context = context;
    }

    public List<GroupAdminDetail> findAdminsByGroupId(int groupId) {
        return context.select(GROUP_ADMIN.GROUP_ID, Tables.USER.ID, Tables.USER.FIRSTNAME, Tables.USER.LASTNAME,
                Tables.USER.EMAIL, Tables.USER.USERNAME)
                .from(GROUP_ADMIN)
                .join(Tables.USER).on(Tables.USER.ID.eq(GROUP_ADMIN.USER_ID))
                .where(GROUP_ADMIN.GROUP_ID.eq(groupId))
                .fetchInto(GroupAdminDetail.class);
    }

    public Boolean checkGroupAdmin(int userId, int groupId) {
        return context.fetchExists(
                context.selectFrom(GROUP_ADMIN)
                        .where(GROUP_ADMIN.USER_ID.eq(userId))
                        .and(GROUP_ADMIN.GROUP_ID.eq(groupId)));
    }

    public GroupContext upsert(GroupContext group) {
        GroupContextRecord groupContextRecord = context.newRecord(GROUP_CONTEXT, group);

        return context.insertInto(GROUP_CONTEXT)
                .set(groupContextRecord)
                .returning(GROUP_CONTEXT.ID, GROUP_CONTEXT.CREATOR, GROUP_CONTEXT.GROUP_TYPE,
                        GROUP_CONTEXT.TITLE, GROUP_CONTEXT.DESCRIPTION)
                .fetchOne()
                .into(GroupContext.class);
    }

    @Override
    public Integer getId(GroupContext groupContext) {
        return groupContext.getId();
    }

    public List<GroupContext> findUserGroups(int exclusionId) {
        return context.selectFrom(Tables.GROUP_CONTEXT)
                .where(GROUP_CONTEXT.ID.eq(exclusionId).not())
                .fetchInto(GroupContext.class);
    }
}
