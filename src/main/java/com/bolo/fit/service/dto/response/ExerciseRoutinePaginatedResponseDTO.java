package com.bolo.fit.service.dto.response;

import com.bolo.fit.model.ExerciseRoutine;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ExerciseRoutinePaginatedResponseDTO {
    private Long routineId;
    private String routineName;
    private List<String> exercises;

    public ExerciseRoutinePaginatedResponseDTO (ExerciseRoutine exerciseRoutine) {
        this.routineId = exerciseRoutine.getExerciseRoutineId();
        this.routineName = exerciseRoutine.getName();
        this.exercises = exerciseRoutine.getExerciseRoutineExercise().stream().map((ex) -> ex.getExercise().getNome()).collect(Collectors.toList());
    }
}
