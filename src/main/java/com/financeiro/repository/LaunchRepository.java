package com.financeiro.repository;

import com.financeiro.model.Launch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LaunchRepository extends JpaRepository<Launch, Long>, JpaSpecificationExecutor<Launch> {}