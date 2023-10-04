package com.sad.t5t1.project.repository;

import com.sad.t5t1.project.model.Match_Result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface Match_ResultRepository extends JpaRepository<Match_Result, String> {}
