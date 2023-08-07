package com.bolo.fit.service;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.Exercise;
import com.bolo.fit.repository.ExerciseRepository;
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

@Service
@Log4j2
public class ExerciseService extends AbstractServiceRepo<ExerciseRepository, Exercise, Long> {
    public ExerciseService(ExerciseRepository repository) {
        super(repository);
    }

    public Page<ExerciseResponseDTO> buscaTodosExercicios(DadosExercicioPaginacaoDTO paginacaoRequest) throws IOException {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Exercise> criteriaQuery = criteriaBuilder.createQuery(Exercise.class);
        List<Predicate> andPredicates = new ArrayList<>();

        Root<Exercise> from = criteriaQuery.from(Exercise.class);
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

    public Exercise findExerciseById(Long exerciseId) throws ApiErrorException {
        return repository.findById(exerciseId).orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.EXERCISE_NOT_FOUND));
    }
}
