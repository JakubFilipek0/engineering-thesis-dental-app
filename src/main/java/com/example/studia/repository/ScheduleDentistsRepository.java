package com.example.studia.repository;

import com.example.studia.model.ScheduleDentists;
import com.example.studia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleDentistsRepository extends JpaRepository<ScheduleDentists, Long> {
    Page<ScheduleDentists> findByDentist(User dentist, Pageable pageable);
    Page<ScheduleDentists> findByState(String state, Pageable pageable);

    List<ScheduleDentists> findByDentist(User dentist);
}
