package com.sad.t5t1.project.repository;

import com.sad.t5t1.project.model.DatabaseFileT1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface DatabaseFileRepository extends JpaRepository<DatabaseFileT1, String> {}
