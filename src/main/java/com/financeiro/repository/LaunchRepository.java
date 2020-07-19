package com.financeiro.repository;

import com.financeiro.model.Launch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LaunchRepository extends JpaRepository<Launch, Long> {

    @Query("select l from Launch l where l.description like %?1%")
    Page<Launch> findLaunchBySearch(String description, Pageable pageable);
}