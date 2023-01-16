package com.example.studia.service;

import com.example.studia.model.Teeth;
import com.example.studia.repository.TeethRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeethServiceImpl implements TeethService{

    @Autowired
    private TeethRepository teethRepository;

    @Override
    public Teeth setTeeth(Teeth teeth) {
        return teethRepository.save(teeth);
    }

    @Override
    public Teeth getTeeth(Long teethId) {
        return teethRepository.findById(teethId).get();
    }

    @Override
    public List<Teeth> getTooth() {
        return teethRepository.findAll();
    }

    @Override
    public void deleteTeeth(Long teethId) {
        teethRepository.deleteById(teethId);
    }
}
