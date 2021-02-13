package com.capstone.project.result;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ResultRepository extends JpaRepository<Result, IdClass> {

    @Query("SELECT t FROM Result t WHERE t.id.username = ?1")
    List<Result> findAllByUsername(String username);
}
