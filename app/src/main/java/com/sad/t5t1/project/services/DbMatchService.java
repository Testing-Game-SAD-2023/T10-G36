package com.sad.t5t1.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sad.t5t1.project.model.Match_Result;
import com.sad.t5t1.project.repository.Match_ResultRepository;

@Service("mainMatchService")
public class DbMatchService implements iMatchService{
    @Autowired
    private Match_ResultRepository matchResultRepository;

    public Match_Result create(Match_Result match){
        return matchResultRepository.save(match);
    }
}
