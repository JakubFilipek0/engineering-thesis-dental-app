package com.example.studia.security;

import com.example.studia.model.User;
import com.example.studia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUser {

    @Autowired
    UserService userService;

    public long getCurrentUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = userDetails.getUsername();
        long userId = -1;
        for (User user : userService.getUsers()) {
            if (user.getUsername().equals(username)) {
                userId = user.getUserId();
                break;
            }
        }
        return userId;
    }

    public User getCurrentUser() {
        User user = userService.getUser(getCurrentUserId());
        return user;
    }
}
