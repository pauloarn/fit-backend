package com.bolo.fit.service.dto.response;

import com.bolo.fit.model.ExerciseRoutine;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRoutinePaginatedResponseDTO {
    private Long routineId;
    private String routineName;
    private String description;

    public ExerciseRoutinePaginatedResponseDTO (ExerciseRoutine exerciseRoutine) {
        this.routineId = exerciseRoutine.getExerciseRoutineId();
        this.routineName = exerciseRoutine.getName();
        this.description = exerciseRoutine.getDescription();
    }
}
