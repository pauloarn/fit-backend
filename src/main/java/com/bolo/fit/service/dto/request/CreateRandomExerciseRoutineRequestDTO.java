package com.bolo.fit.service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRandomExerciseRoutineRequestDTO {
    private Long bodyPartId;
    private Long exerciseTypeId;
    private Long equipmentTypeId;
    private Integer amountOfExercises;
}
