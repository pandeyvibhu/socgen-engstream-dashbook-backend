package com.bookmark.dashbook.service;

import com.bookmark.dashbook.dao.UserDao;
import com.dashbook.bookmark.jooq.model.Tables;
import com.dashbook.bookmark.jooq.model.tables.pojos.User;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(this.findUserByUsername(username));
        user.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
    }

    public User getCurrentUserDetails() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return findUserByUsername(username);
    }

    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    public void signupUser(User user) {
        userDao.save(user);
    }

}