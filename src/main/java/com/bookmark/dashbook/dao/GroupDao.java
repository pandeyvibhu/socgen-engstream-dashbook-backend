package com.bookmark.dashbook.dao;

import com.dashbook.bookmark.jooq.model.tables.pojos.GroupContext;
import com.dashbook.bookmark.jooq.model.tables.records.GroupContextRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.dashbook.bookmark.jooq.model.tables.GroupContext.GROUP_CONTEXT;

@Repository
public class GroupDao extends DAOImpl<GroupContextRecord, GroupContext, Integer> {

    @Autowired
    DSLContext context;

    public GroupDao(DSLContext context) {
        super(GROUP_CONTEXT, GroupContext.class, context.configuration());
        this.context = context;
    }

    public GroupContext upsert(GroupContext group) {
        GroupContextRecord groupContextRecord = context.newRecord(GROUP_CONTEXT, group);

        return context.insertInto(GROUP_CONTEXT)
                .set(groupContextRecord)
                .onConflict(GROUP_CONTEXT.TITLE)
                .doUpdate()
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
}
