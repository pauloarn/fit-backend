package com.bolo.fit.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateRandomExerciseRoutineRequestDTO {
    private Long bodyPartId;
    private Long exerciseTypeId;
    private Long equipmentTypeId;
    private Integer amountOfExercises;
}
