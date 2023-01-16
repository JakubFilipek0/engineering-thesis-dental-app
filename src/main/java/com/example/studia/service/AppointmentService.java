package com.example.studia.service;

import com.example.studia.model.Appointment;
import com.example.studia.model.Blog;
import com.example.studia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {

    Appointment setAppointment(Appointment appointment);
    Appointment getAppointment(Long appointmentId);
    List<Appointment> getAppointments();
    void deleteAppointment(Long appointmentId);
    Page<Appointment> findPaginatedByUser(int pageNumber, User user);
    Page<Appointment> findPaginatedByDentist(int pageNumber, User dentist);
    Page<Appointment> findPaginatedByDentistAndStatus(int pageNumber, User dentist, String status, String sortField, String sortDirection);
    Page<Appointment> findPaginatedByUserAndStatus(int pageNumber, User user, String status, String sortField, String sortDirection);
    Page<Appointment> findPaginatedByUserAndStatusDouble(int pageNumber, User user, String status, String status2, String sortField, String sortDirection);
    Page<Appointment> findPaginatedByDentistAndStatusDouble(int pageNumber, User dentist, String status, String status2, String sortField, String sortDirection);
}
