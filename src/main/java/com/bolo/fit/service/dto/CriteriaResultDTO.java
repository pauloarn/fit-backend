package com.bolo.fit.service.dto;

import com.bolo.fit.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CriteriaResultDTO {

  private  CriteriaQuery<Exercise> criteriaQuery;
  private  List<Predicate> andPredicates;
  private   Root<Exercise> from;
  private CriteriaBuilder criteriaBuilder;
}
