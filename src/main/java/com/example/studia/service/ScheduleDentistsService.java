package com.example.studia.service;

import com.example.studia.model.ScheduleDentists;
import com.example.studia.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ScheduleDentistsService {

    ScheduleDentists setScheduleDentists(ScheduleDentists scheduleDentists);
    List<ScheduleDentists> findByDentist(User user);
    ScheduleDentists getScheduleDentists(Long scheduleDentistsId);
    List<ScheduleDentists> getScheduleDentistsAll();
    void deleteScheduleDentists(Long scheduleDentistsId);
    Page<ScheduleDentists> findPaginatedByDentist(int pageNumber, User dentist, String sortField, String sortDirection);
    Page<ScheduleDentists> findPaginatedByStatus(int pageNumber, String status, String sortField, String sortDirection);
}
