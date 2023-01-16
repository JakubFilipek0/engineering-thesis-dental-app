package com.example.studia.service;

import com.example.studia.model.Appointment;
import com.example.studia.model.User;
import com.example.studia.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment setAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointment(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).get();
    }

    @Override
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public Page<Appointment> findPaginatedByUser(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 3);
        return appointmentRepository.findByUser(user, pageable);
    }

    @Override
    public Page<Appointment> findPaginatedByDentist(int pageNumber, User dentist) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 3);

        return appointmentRepository.findByDentist(dentist, pageable);
    }

    @Override
    public Page<Appointment> findPaginatedByDentistAndStatus(int pageNumber, User dentist, String status, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 3, sort);
        return appointmentRepository.findByDentistAndStatus(dentist, status, pageable);
    }

    @Override
    public Page<Appointment> findPaginatedByUserAndStatus(int pageNumber, User user, String status, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 3, sort);
        return appointmentRepository.findByUserAndStatus(user, status, pageable);
    }

    @Override
    public Page<Appointment> findPaginatedByUserAndStatusDouble(int pageNumber, User user, String status, String status2, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 3, sort);
        return appointmentRepository.findByUserAndStatusOrStatus(user, status, status2, pageable);
    }

    @Override
    public Page<Appointment> findPaginatedByDentistAndStatusDouble(int pageNumber, User dentist, String status, String status2, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 3, sort);
        return appointmentRepository.findByDentistAndStatusOrStatus(dentist, status, status2, pageable);
    }
}
