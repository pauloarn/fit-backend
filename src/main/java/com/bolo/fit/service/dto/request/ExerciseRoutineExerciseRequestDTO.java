package com.bolo.fit.service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRoutineExerciseRequestDTO {
    private Long exerciseId;
    private Integer repetitions;
    private Integer series;
    private Double restTime;
    private String observation;
}
