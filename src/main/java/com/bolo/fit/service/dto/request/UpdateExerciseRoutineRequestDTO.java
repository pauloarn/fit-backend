package com.bolo.fit.service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateExerciseRoutineRequestDTO {
    private String routineName;
    private Integer repetitions;
    private Integer series;
    private Double restTime;
    private String observation;
    private List<ExerciseRoutineExerciseRequestDTO> exerciseList;
}
