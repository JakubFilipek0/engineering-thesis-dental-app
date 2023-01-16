package com.example.studia.repository;

import com.example.studia.model.PasswordHistory;
import com.example.studia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
    PasswordHistory findByUser(User user);
}
