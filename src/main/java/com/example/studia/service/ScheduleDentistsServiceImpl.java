package com.example.studia.service;

import com.example.studia.model.ScheduleDentists;
import com.example.studia.model.User;
import com.example.studia.repository.ScheduleDentistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduleDentistsServiceImpl implements ScheduleDentistsService {

    @Autowired
    private ScheduleDentistsRepository scheduleDentistsRepository;

    @Override
    public ScheduleDentists setScheduleDentists(ScheduleDentists scheduleDentists) {
        return scheduleDentistsRepository.save(scheduleDentists);
    }

    @Override
    public List<ScheduleDentists> findByDentist(User user) {
        return scheduleDentistsRepository.findByDentist(user);
    }

    @Override
    public ScheduleDentists getScheduleDentists(Long scheduleDentistsId) {
        return scheduleDentistsRepository.findById(scheduleDentistsId).get();
    }

    @Override
    public List<ScheduleDentists> getScheduleDentistsAll() {
        return scheduleDentistsRepository.findAll();
    }

    @Override
    public void deleteScheduleDentists(Long scheduleDentistsId) {
        scheduleDentistsRepository.deleteById(scheduleDentistsId);
    }

    @Override
    public Page<ScheduleDentists> findPaginatedByDentist(int pageNumber, User dentist, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 6, sort);
        return scheduleDentistsRepository.findByDentist(dentist, pageable);
    }

    @Override
    public Page<ScheduleDentists> findPaginatedByStatus(int pageNumber, String status, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 6, sort);
        return scheduleDentistsRepository.findByState(status, pageable);
    }
}
