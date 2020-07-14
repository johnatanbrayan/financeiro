package com.financeiro.repository;

import com.financeiro.model.Launch;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LaunchRepository extends JpaRepository<Launch, Long> {}