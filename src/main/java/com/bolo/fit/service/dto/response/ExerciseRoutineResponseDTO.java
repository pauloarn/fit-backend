package com.bolo.fit.service.dto.response;

import com.bolo.fit.model.ExerciseRoutine;
import lombok.Data;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExerciseRoutineResponseDTO {
    private Long routineId;
    private String routineName;
    private String description;
    private Integer series;
    private Integer repetitions;
    private Double restTime;
    private List<RoutineExerciseResponseDTO> listRoutineExercise;
    public ExerciseRoutineResponseDTO (ExerciseRoutine exerciseRoutine, Boolean shoundGetImageData){
        this.routineId = exerciseRoutine.getExerciseRoutineId();
        this.routineName = exerciseRoutine.getName();
        this.description = exerciseRoutine.getDescription();
        this.series = exerciseRoutine.getSeries();
        this.repetitions = exerciseRoutine.getRepetitions();
        this.restTime = exerciseRoutine.getRestTime();
        this.listRoutineExercise = exerciseRoutine.getExerciseRoutineExercise().stream()
                .map((ere) -> {
                    try {
                        return new RoutineExerciseResponseDTO(ere, shoundGetImageData);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }
}
