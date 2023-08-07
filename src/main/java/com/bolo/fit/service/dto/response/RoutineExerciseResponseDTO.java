package com.bolo.fit.service.dto.response;

import com.bolo.fit.model.ExerciseRoutineExercise;
import lombok.Data;

import java.io.IOException;

@Data
public class RoutineExerciseResponseDTO {
    private Long routineExerciseId;
    private Integer series;
    private Integer repetitions;
    private String observation;
    private Double restTime;
    private ExerciseResponseDTO execise;
    public RoutineExerciseResponseDTO (ExerciseRoutineExercise routineExercise, Boolean shouldGetImageData) throws IOException {
        this.routineExerciseId = routineExercise.getExerciseRoutineId();
        this.series = routineExercise.getSeries();
        this.repetitions = routineExercise.getRepetitions();
        this.observation = routineExercise.getNotes();
        this.restTime = routineExercise.getRest_time();
        this.execise = new ExerciseResponseDTO(routineExercise.getExercise(), shouldGetImageData);
    }
}
