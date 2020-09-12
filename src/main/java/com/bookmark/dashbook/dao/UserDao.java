package com.bookmark.dashbook.dao;

import com.dashbook.bookmark.jooq.model.Tables;
import com.dashbook.bookmark.jooq.model.tables.pojos.User;
import com.dashbook.bookmark.jooq.model.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.dashbook.bookmark.jooq.model.tables.User.USER;

@Repository
public class UserDao extends DAOImpl<UserRecord, User, Integer> {

    @Autowired
    DSLContext context;

    public UserDao(DSLContext context) {
        super(USER, User.class, context.configuration());
        this.context = context;
    }

    public User findUserByUsername(String username) {
        return context
                .selectFrom(Tables.USER)
                .where(Tables.USER.USERNAME.eq(username))
                .limit(1)
                .fetchOneInto(User.class);
    }

    public void save(User user) {
        context.insertInto(Tables.USER, Tables.USER.FIRSTNAME, Tables.USER.LASTNAME,
                Tables.USER.EMAIL, Tables.USER.USERNAME, Tables.USER.PASSWORD)
                .values(user.getFirstname(), user.getLastname(), user.getEmail(), user.getUsername(), user.getPassword())
                .execute();
    }

    @Override
    public Integer getId(User user) {
        return user.getId();
    }
}
