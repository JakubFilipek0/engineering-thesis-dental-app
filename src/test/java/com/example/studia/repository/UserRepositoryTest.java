package com.example.studia.repository;

import com.example.studia.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByRole() {
        User user = new User("user", "Jakub", "Filipek", "user", "user");
        user.setRole("ROLE_USER");
        userRepository.save(user);

        User admin = new User("admin", "Jan", "Kowalski", "admin", "admin");
        admin.setRole("ROLE_ADMIN");
        userRepository.save(admin);

        List<User> userList = userRepository.findByRole("ROLE_USER");

        assertEquals(1, userList.size());
    }
}