package com.bolo.fit.service;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.BodyPart;
import com.bolo.fit.model.EquipmentType;
import com.bolo.fit.model.Exercise;
import com.bolo.fit.model.ExerciseType;
import com.bolo.fit.repository.ExerciseRepository;
import com.bolo.fit.service.dto.CriteriaResultDTO;
import com.bolo.fit.service.dto.request.CreateRandomExerciseRoutineRequestDTO;
import com.bolo.fit.service.dto.request.DadosExercicioPaginacaoDTO;
import com.bolo.fit.service.dto.response.ExerciseResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
public class ExerciseService extends AbstractServiceRepo<ExerciseRepository, Exercise, Long> {
    public ExerciseService(ExerciseRepository repository) {
        super(repository);
    }

    public Page<ExerciseResponseDTO> buscaTodosExercicios(DadosExercicioPaginacaoDTO paginacaoRequest) throws IOException {
        CreateRandomExerciseRoutineRequestDTO baseDto = new CreateRandomExerciseRoutineRequestDTO(
                paginacaoRequest.getBodyPartId(),
                paginacaoRequest.getExerciseTypeId(),
                paginacaoRequest.getEquipmentTypeId(),
                null
        );
        CriteriaResultDTO baseCriteria = getBaseCriteria(baseDto);
        var andPredicates = baseCriteria.getAndPredicates();
        var criteriaBuilder = baseCriteria.getCriteriaBuilder();
        var criteriaQuery = baseCriteria.getCriteriaQuery();
        var from = baseCriteria.getFrom();

        if(Objects.nonNull(paginacaoRequest.getSearchText())){
            andPredicates.add(criteriaBuilder.like(from.get("nome"), "%"+paginacaoRequest.getSearchText()+"%"));
        }

        criteriaQuery = criteriaQuery.select(from).where(andPredicates.toArray(new Predicate[0]));
        List<Order> orderList = new ArrayList<>();
        orderList.add(criteriaBuilder.asc(from.get("name")));
        criteriaQuery.orderBy(orderList.toArray(new Order[0]));
        TypedQuery<Exercise> query = em.createQuery(criteriaQuery);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Exercise> countRoot = countQuery.from(criteriaQuery.getResultType());
        countRoot.alias(from.getAlias());
        countQuery.select(criteriaBuilder.count(countRoot)).where(criteriaQuery.getRestriction());
        Long totalExercises = em.createQuery(countQuery).getSingleResult();
        log.info("Total de Exercicios encontrados: {}", totalExercises);

        int page = paginacaoRequest.getPage();
        int sizePage = paginacaoRequest.getSizePage();
        Pageable pageable = generatePageable(page, sizePage);
        page = pageable.getPageNumber();
        query.setFirstResult(page * sizePage);
        query.setMaxResults(sizePage);
        log.info("Buscando os exercicios");
        List<Exercise> listaExercises = query.getResultList();
        List<ExerciseResponseDTO> exercisesDTO = new ArrayList<>();
        for (Exercise ex : listaExercises) {
            ExerciseResponseDTO exDto = new ExerciseResponseDTO(ex, paginacaoRequest.getGetImageBase64());
            exercisesDTO.add(exDto);
        }

        return new PageImpl<>(exercisesDTO, pageable, totalExercises);
    }

    public List<Exercise> findExercisesForRandomRoutine(CreateRandomExerciseRoutineRequestDTO exerciseFilters){
        CriteriaResultDTO result = getBaseCriteria(exerciseFilters);
        CriteriaQuery<Exercise> criteriaQuery;

        criteriaQuery = result.getCriteriaQuery().select(result.getFrom()).where(result.getAndPredicates().toArray(new Predicate[0]));
        TypedQuery<Exercise> query = em.createQuery(criteriaQuery);

        return query.getResultList();
    }

    private CriteriaResultDTO getBaseCriteria(CreateRandomExerciseRoutineRequestDTO exerciseFilters) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Exercise> criteriaQuery = criteriaBuilder.createQuery(Exercise.class);
        List<Predicate> andPredicates = new ArrayList<>();
        Root<Exercise> from = criteriaQuery.from(Exercise.class);
        Join<Exercise, BodyPart> joinExerciseBodyPart = from.join("bodyPart");
        Join<Exercise, ExerciseType> joinExerciseExerciseType= from.join("exerciseType");
        Join<Exercise, EquipmentType> joinExerciseEquipmentType = from.join("equipmentType");
        joinExerciseBodyPart.alias("bodyPart");
        joinExerciseExerciseType.alias("exerciseType");
        joinExerciseEquipmentType.alias("equipmentType");

        if(Objects.nonNull(exerciseFilters.getExerciseTypeId())){
            andPredicates.add(criteriaBuilder.equal(joinExerciseExerciseType.get("execiseId"), exerciseFilters.getExerciseTypeId()));
        }
        if(Objects.nonNull(exerciseFilters.getBodyPartId())){
            andPredicates.add(criteriaBuilder.equal(joinExerciseBodyPart.get("bodyPartId"), exerciseFilters.getBodyPartId()));
        }
        if(Objects.nonNull(exerciseFilters.getEquipmentTypeId())){
            andPredicates.add(criteriaBuilder.equal(joinExerciseEquipmentType.get("equipmentTypeId"), exerciseFilters.getEquipmentTypeId()));
        }

        return new CriteriaResultDTO(criteriaQuery, andPredicates, from, criteriaBuilder);
    }
    public Exercise findExerciseById(Long exerciseId) throws ApiErrorException {
        return repository.findById(exerciseId).orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.EXERCISE_NOT_FOUND));
    }
}
