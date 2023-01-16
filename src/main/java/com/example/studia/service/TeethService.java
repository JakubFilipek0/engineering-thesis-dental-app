package com.example.studia.service;

import com.example.studia.model.Teeth;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeethService {

    Teeth setTeeth(Teeth teeth);
    Teeth getTeeth(Long teethId);
    List<Teeth> getTooth();
    void deleteTeeth(Long teethId);
}
