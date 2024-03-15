package com.bolo.fit.service.dto;

import com.bolo.fit.model.Exercise;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CriteriaResultDTO {

    private CriteriaQuery<Exercise> criteriaQuery;
    private List<Predicate> andPredicates;
    private Root<Exercise> from;
    private CriteriaBuilder criteriaBuilder;
}
