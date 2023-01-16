package com.example.studia.repository;

import com.example.studia.model.PasswordHistory;
import com.example.studia.model.User;
import com.example.studia.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordHistoryRepositoryTest {

    @Autowired
    UserService userService;

    @Autowired
    PasswordHistoryRepository passwordHistoryRepository;

    @Test
    void findByUser() {
        User user = new User("user", "Jakub", "Filipek", "user", "user");
        user.setRole("ROLE_USER");
        userService.setUser(user);
        PasswordHistory passwordHistoryUser = new PasswordHistory();
        passwordHistoryUser.setPointer(0); // wskaźnik
        passwordHistoryUser.setUser(user); // jaki User
        List<String> passwordUser = new ArrayList<>();
        passwordUser.add(user.getPassword());
        passwordUser.add("1"); passwordUser.add("2");passwordUser.add("3");passwordUser.add("4");passwordUser.add("5");
        passwordUser.add("6");passwordUser.add("7");passwordUser.add("8");passwordUser.add("9");
        passwordHistoryUser.setPasswordsList(passwordUser); // pierwsze hasło do listy
        passwordHistoryRepository.save(passwordHistoryUser);

        //PasswordHistory history = passwordHistoryRepository.findByUser(user);

        assertEquals(passwordHistoryRepository.findByUser(user).getId(), passwordHistoryUser.getId());
    }
}