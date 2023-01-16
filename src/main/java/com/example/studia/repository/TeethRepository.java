package com.example.studia.repository;

import com.example.studia.model.Teeth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeethRepository extends JpaRepository<Teeth, Long> {
}
