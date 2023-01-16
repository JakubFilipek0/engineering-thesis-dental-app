package com.example.studia.repository;

import com.example.studia.model.Appointment;
import com.example.studia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByUser(User user, Pageable pageable);
    Page<Appointment> findByDentist(User dentist, Pageable pageable);
    Page<Appointment> findByStatus(String status, Pageable pageable);
    Page<Appointment> findByDentistAndStatus(User dentist, String status, Pageable pageable);
    Page<Appointment> findByUserAndStatus(User user, String status, Pageable pageable);
    Page<Appointment> findByUserAndStatusOrStatus(User user, String status, String status2, Pageable pageable);
    Page<Appointment> findByDentistAndStatusOrStatus(User dentist, String status, String status2, Pageable pageable);
}
