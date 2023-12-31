package com.bolo.fit.service;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.Exercise;
import com.bolo.fit.model.ExerciseRoutine;
import com.bolo.fit.model.ExerciseRoutineExercise;
import com.bolo.fit.model.User;
import com.bolo.fit.repository.ExerciseRoutineRepository;
import com.bolo.fit.service.dto.request.*;
import com.bolo.fit.service.dto.response.ExerciseRoutinePaginatedResponseDTO;
import com.bolo.fit.service.dto.response.ExerciseRoutineResponseDTO;
import com.bolo.fit.utils.RandomUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ExerciseRoutineService extends AbstractServiceRepo<ExerciseRoutineRepository, ExerciseRoutine, Long>{

    private final ExerciseService exerciseService;

    private final UserService userService;

    public ExerciseRoutineService(ExerciseRoutineRepository repository, ExerciseService exerciseService, UserService userService) {
        super(repository);
        this.exerciseService = exerciseService;
        this.userService = userService;
    }

    public Page<ExerciseRoutinePaginatedResponseDTO> getAllRoutines(DadosExercicioPaginacaoDTO paginacaoRequest) {
        User loggedUser = userService.getLoggedUser();
        int page = paginacaoRequest.getPage();
        int sizePage = paginacaoRequest.getSizePage();
        Pageable pageable = generatePageable(page, sizePage);
        log.info("Buscando os exercicios");
        List<ExerciseRoutine> listaExercises = loggedUser.getListExerciseRoutine();
        List<ExerciseRoutinePaginatedResponseDTO> resultDTO = listaExercises.stream()
                .filter(ExerciseRoutine::getIsVisible)
                .map(ExerciseRoutinePaginatedResponseDTO::new)
                .collect(Collectors.toList());
        return new PageImpl<>(resultDTO, pageable, listaExercises.size());
    }

    public ExerciseRoutineResponseDTO getExerciseRoutineDetail(Long exerciseRoutineId) throws ApiErrorException {
        log.info("Searching Exercise Routine");
        ExerciseRoutine er = repository.findById(exerciseRoutineId).orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.ROUTINE_NOT_FOUND));
        log.info("Exercise Routine Found");
        return new ExerciseRoutineResponseDTO(er, true);
    }

    public ExerciseRoutineResponseDTO generateRandomRoutine(CreateRandomExerciseRoutineRequestDTO createRandomExerciseRoutine) throws ApiErrorException {
        log.info("Creating Exercise Routine With random exercises");
        User loggedUser = userService.getLoggedUser();
        List<Exercise> selectedExercises = exerciseService.findExercisesForRandomRoutine(createRandomExerciseRoutine);
        List<Exercise> filteredExercises = new ArrayList<>();
        if(selectedExercises.isEmpty()){
            throw new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.EXERCISE_NOT_FOUND);
        }
        for (int i = 0; i < createRandomExerciseRoutine.getAmountOfExercises() ; i++) {
            Exercise currentExercise = selectedExercises.get(RandomUtils.selectNumberInRange(selectedExercises.size()));
                if(filteredExercises.contains(currentExercise)){
                    continue;
                }
                filteredExercises.add(selectedExercises.get(RandomUtils.selectNumberInRange(selectedExercises.size())));
        }
        ExerciseRoutine routine = new ExerciseRoutine();
        routine.setUser(loggedUser);
        routine.setName("Nova Rotina de "+ filteredExercises.get(0).getBodyPart().getNome());
        routine.setDescription("");
        routine.setRepetitions(15);
        routine.setSeries(3);
        routine.setIsVisible(true);
        routine.setRestTime(1.0);
        List<ExerciseRoutineExercise> exerciseList = new ArrayList<>();
        for(Exercise ex :  filteredExercises){
            ExerciseRoutineExercise ere = new ExerciseRoutineExercise();
            ere.setSeries(null);
            ere.setRepetitions(null);
            ere.setRest_time(null);
            ere.setNotes(null);
            ere.setExerciseRoutine(routine);
            ere.setExercise(ex);
            exerciseList.add(ere);
        };
        routine.setExerciseRoutineExercise(exerciseList);
        ExerciseRoutine er = repository.save(routine);
        log.info("Exercise Routine Created");
        return new ExerciseRoutineResponseDTO(er, true);
    }

    public ExerciseRoutineResponseDTO createExerciseRoutine(CreateExerciseRoutineRequestDTO newRoutineData) throws ApiErrorException {
        log.info("Initializing Exercise Routine Creation");
        User loggedUser = userService.getLoggedUser();
        ExerciseRoutine routine = new ExerciseRoutine();
        routine.setUser(loggedUser);
        routine.setName(newRoutineData.getRoutineName());
        routine.setDescription(newRoutineData.getObservation());
        routine.setRepetitions(newRoutineData.getRepetitions());
        routine.setSeries(newRoutineData.getSeries());
        routine.setIsVisible(true);
        routine.setRestTime(newRoutineData.getRestTime());
        List<ExerciseRoutineExercise> exerciseList = getExerciseRoutineExercisesFromExerciseList(newRoutineData.getExerciseList(), routine);
        routine.setExerciseRoutineExercise(exerciseList);
        ExerciseRoutine er = repository.save(routine);
        log.info("Exercise Routine Created");
        return new ExerciseRoutineResponseDTO(er, true);
    }

    public void deleteExerciseRoutine(Long exerciseRoutineId) throws ApiErrorException {
        ExerciseRoutine routine = repository.findById(exerciseRoutineId).orElseThrow(() -> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.ROUTINE_NOT_FOUND));
        routine.setIsVisible(false);
        repository.save(routine);
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
        List<ExerciseRoutineExercise> exerciseList = getExerciseRoutineExercisesFromExerciseList(routineRequestDTO.getExerciseList(), routine);
        routine.getExerciseRoutineExercise().addAll(exerciseList);
        ExerciseRoutine er = repository.save(routine);
        log.info("Exercise Routine Updated");
        return new ExerciseRoutineResponseDTO(er, true);
    }

    private List<ExerciseRoutineExercise> getExerciseRoutineExercisesFromExerciseList(List<ExerciseRoutineExerciseRequestDTO> routineRequestDTO, ExerciseRoutine routine)
            throws ApiErrorException {
        List<ExerciseRoutineExercise> exerciseRoutineList = new ArrayList<>();
        for (ExerciseRoutineExerciseRequestDTO ex : routineRequestDTO) {
            ExerciseRoutineExercise ere = new ExerciseRoutineExercise();
            List<Exercise> exerciseList = new ArrayList<>();
            for(Long exerciseId : ex.getBiSetExercises()){
                Exercise exrcise = exerciseService.findExerciseById(exerciseId);
                exerciseList.add(exrcise);
            }
            Exercise exr = exerciseService.findExerciseById(ex.getExerciseId());
            ere.setSeries(ex.getSeries());
            ere.setSecondaryExercisesList(exerciseList);
            ere.setRepetitions(ex.getRepetitions());
            ere.setRest_time(ex.getRestTime());
            ere.setNotes(ex.getObservation());
            ere.setExerciseRoutine(routine);
            ere.setExercise(exr);
            exerciseRoutineList.add(ere);
        }
        return exerciseRoutineList;
    }
}
