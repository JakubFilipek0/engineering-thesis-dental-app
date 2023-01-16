package com.example.studia.service;

import com.example.studia.model.Opinion;
import com.example.studia.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpinionServiceImpl implements OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;

    @Override
    public Opinion setOpinion(Opinion opinion) {
        return opinionRepository.save(opinion);
    }

    @Override
    public Opinion getOpinion(Long opinionId) {
        return opinionRepository.findById(opinionId).get();
    }

    @Override
    public List<Opinion> getOpinions() {
        return opinionRepository.findAll();
    }

    @Override
    public void deleteOpinion(Long opinionId) {
        opinionRepository.deleteById(opinionId);
    }
}
