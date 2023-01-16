package com.example.studia.repository;

import com.example.studia.model.ScheduleDentists;
import com.example.studia.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleDentistsRepositoryTest {

    @Autowired
    ScheduleDentistsRepository scheduleDentistsRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void findByDentist() {
        User dentist = new User("dentist", "Jan", "Kowal", "dentist", "dentist");
        dentist.setRole("ROLE_DENTIST");
        userRepository.save(dentist);

        User dentist2 = new User("dentist2", "Jan2", "Kowal2", "dentist2", "dentist2");
        dentist2.setRole("ROLE_DENTIST");
        userRepository.save(dentist2);

        ScheduleDentists scheduleDentists = new ScheduleDentists(new Date(), "9:00", "OPEN", dentist);
        scheduleDentistsRepository.save(scheduleDentists);

        ScheduleDentists scheduleDentists2 = new ScheduleDentists(new Date(), "9:00", "OPEN", dentist2);
        scheduleDentistsRepository.save(scheduleDentists2);

        assertEquals(1, scheduleDentistsRepository.findByDentist(dentist).size());
    }
}