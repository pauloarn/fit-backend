package com.bolo.fit.service;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.Exercise;
import com.bolo.fit.model.ExerciseRoutine;
import com.bolo.fit.model.ExerciseRoutineExercise;
import com.bolo.fit.repository.ExerciseRoutineRepository;
import com.bolo.fit.service.dto.request.CreateExerciseRoutineRequestDTO;
import com.bolo.fit.service.dto.request.DadosExercicioPaginacaoDTO;
import com.bolo.fit.service.dto.request.ExerciseRoutineExerciseRequestDTO;
import com.bolo.fit.service.dto.request.UpdateExerciseRoutineRequestDTO;
import com.bolo.fit.service.dto.response.ExerciseRoutinePaginatedResponseDTO;
import com.bolo.fit.service.dto.response.ExerciseRoutineResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ExerciseRoutineService extends AbstractServiceRepo<ExerciseRoutineRepository, ExerciseRoutine, Long>{

    private final ExerciseService exerciseService;

    public ExerciseRoutineService(ExerciseRoutineRepository repository, ExerciseService exerciseService) {
        super(repository);
        this.exerciseService = exerciseService;
    }

    public Page<ExerciseRoutinePaginatedResponseDTO> getAllRoutines(DadosExercicioPaginacaoDTO paginacaoRequest) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<ExerciseRoutine> criteriaQuery = criteriaBuilder.createQuery(ExerciseRoutine.class);
        List<Predicate> andPredicates = new ArrayList<>();

        Root<ExerciseRoutine> from = criteriaQuery.from(ExerciseRoutine.class);

        andPredicates.add(criteriaBuilder.isTrue(from.get("isVisible")));

        criteriaQuery = criteriaQuery.select(from).where(andPredicates.toArray(new Predicate[0]));
        List<Order> orderList = new ArrayList<>();
        orderList.add(criteriaBuilder.asc(from.get("createdAt")));
        criteriaQuery.orderBy(orderList.toArray(new Order[0]));
        TypedQuery<ExerciseRoutine> query = em.createQuery(criteriaQuery);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<ExerciseRoutine> countRoot = countQuery.from(criteriaQuery.getResultType());
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
        List<ExerciseRoutine> listaExercises = query.getResultList();
        List<ExerciseRoutinePaginatedResponseDTO> resultDTO = listaExercises.stream().map(ExerciseRoutinePaginatedResponseDTO::new).collect(Collectors.toList());
        return new PageImpl<>(resultDTO, pageable, totalExercises);
    }

    public ExerciseRoutineResponseDTO getExerciseRoutineDetail(Long exerciseRoutineId) throws ApiErrorException {
        log.info("Searching Exercise Routine");
        ExerciseRoutine er = repository.findById(exerciseRoutineId).orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.ROUTINE_NOT_FOUND));
        log.info("Exercise Routine Found");
        return new ExerciseRoutineResponseDTO(er, true);
    }

    public ExerciseRoutineResponseDTO createExerciseRoutine(CreateExerciseRoutineRequestDTO newRoutineData) throws ApiErrorException {
        log.info("Initializing Exercise Routine Creation");
        ExerciseRoutine routine = new ExerciseRoutine();
        routine.setName(newRoutineData.getRoutineName());
        routine.setDescription(newRoutineData.getObservation());
        routine.setRepetitions(newRoutineData.getRepetitions());
        routine.setSeries(newRoutineData.getSeries());
        routine.setIsVisible(true);
        routine.setRestTime(newRoutineData.getRestTime());
        List<ExerciseRoutineExercise> exerciseList = new ArrayList<>();
        for(ExerciseRoutineExerciseRequestDTO ex :  newRoutineData.getExerciseList()){
            ExerciseRoutineExercise ere = new ExerciseRoutineExercise();
            Exercise exr = exerciseService.findExerciseById(ex.getExerciseId());
            ere.setSeries(ex.getSeries());
            ere.setRepetitions(ex.getRepetitions());
            ere.setRest_time(ex.getRestTime());
            ere.setNotes(ex.getObservation());
            ere.setExerciseRoutine(routine);
            ere.setExercise(exr);
            exerciseList.add(ere);
        };
        routine.setExerciseRoutineExercise(exerciseList);
        ExerciseRoutine er = repository.save(routine);
        log.info("Exercise Routine Created");
        return new ExerciseRoutineResponseDTO(er, true);
    }

    public ExerciseRoutineResponseDTO updateExerciseRoutine(Long routineId, UpdateExerciseRoutineRequestDTO routineRequestDTO) throws ApiErrorException {
        log.info("Initializing Exercise Routine Update");
        ExerciseRoutine routine = repository.findById(routineId).orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.ROUTINE_NOT_FOUND));
        routine.setName(routineRequestDTO.getRoutineName());
        routine.setDescription(routineRequestDTO.getObservation());
        routine.setRepetitions(routineRequestDTO.getRepetitions());
        routine.setSeries(routineRequestDTO.getSeries());
        routine.setRestTime(routineRequestDTO.getRestTime());
        routine.getExerciseRoutineExercise().clear();
        List<ExerciseRoutineExercise> exerciseList = new ArrayList<>();
        for(ExerciseRoutineExerciseRequestDTO ex :  routineRequestDTO.getExerciseList()){
            ExerciseRoutineExercise ere = new ExerciseRoutineExercise();
            Exercise exr = exerciseService.findExerciseById(ex.getExerciseId());
            ere.setSeries(ex.getSeries());
            ere.setRepetitions(ex.getRepetitions());
            ere.setRest_time(ex.getRestTime());
            ere.setNotes(ex.getObservation());
            ere.setExerciseRoutine(routine);
            ere.setExercise(exr);
            exerciseList.add(ere);
        };
        routine.getExerciseRoutineExercise().addAll(exerciseList);
        ExerciseRoutine er = repository.save(routine);
        log.info("Exercise Routine Updated");
        return new ExerciseRoutineResponseDTO(er, true);
    }
}
