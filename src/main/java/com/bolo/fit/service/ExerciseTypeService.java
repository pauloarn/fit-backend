package com.bolo.fit.service;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.ExerciseType;
import com.bolo.fit.repository.ExerciseTypeRepository;
import com.bolo.fit.service.dto.response.ExerciseTypeResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ExerciseTypeService extends AbstractServiceRepo<ExerciseTypeRepository, ExerciseType, Long>{
    public ExerciseTypeService(ExerciseTypeRepository repository) {
        super(repository);
    }

    public List<ExerciseTypeResponseDTO> getAllExerciseTypes() {
        List<ExerciseType> exerciseTypes = repository.findAll();
        log.info("Searching all body parts");
        return exerciseTypes.stream().map((et)-> new ExerciseTypeResponseDTO().fromExerciseType(et)).collect(Collectors.toList());
    }

    public ExerciseType getExerciseTypeById(Long exerciseTypeId) throws ApiErrorException {
        return repository.findById(exerciseTypeId).orElseThrow(()-> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.EXERCISE_TYPE_NOT_FOUND));
    }
}
