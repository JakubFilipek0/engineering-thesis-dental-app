package com.example.studia.service;

import com.example.studia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getUser(Long userId);
    List<User> getUsers();
    User setUser(User user);
    void deleteUser(Long userId);

    List<User> getDentistList();
}
