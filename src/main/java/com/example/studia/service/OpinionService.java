package com.example.studia.service;

import com.example.studia.model.Opinion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpinionService {

    Opinion setOpinion(Opinion opinion);
    Opinion getOpinion(Long opinionId);
    List<Opinion> getOpinions();
    void deleteOpinion(Long opinionId);
}
