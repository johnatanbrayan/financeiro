package com.financeiro.service.query;

import java.time.LocalDate;

import com.financeiro.model.Launch;
import com.financeiro.model.Launch_;

import org.springframework.data.jpa.domain.Specification;

public class LaunchSpecification {
 
    public static Specification<Launch> searchByDescription(String description) {
        return (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.like(criteriaBuilder.lower(root.get(Launch_.description)), "%" + description.toLowerCase() + "%");  
    }

    public static Specification<Launch> searchByDueDateFrom(LocalDate dueDateFrom) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(Launch_.dueDate), dueDateFrom);
    }

    public static Specification<Launch> searchByDueDateUntil(LocalDate dueDateUntil) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(Launch_.dueDate), dueDateUntil);
    }
}