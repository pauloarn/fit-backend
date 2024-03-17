package com.bolo.fit.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRandomExerciseRoutineRequestDTO {
  private List<Long> bodyPartList;
  private List<Long> exerciseTypeList;
  private List<Long> equipmentTypeList;
  private Integer amountOfExercises;

  public static CreateRandomExerciseRoutineRequestDTO fromSearch(Long bodyPartId,
                                                                 Long exerciseTypeId,
                                                                 Long equipmentTypeId,
                                                                 Integer amountOfExercises) {
    CreateRandomExerciseRoutineRequestDTO dto = new CreateRandomExerciseRoutineRequestDTO();
    if (Objects.nonNull(bodyPartId)) {
      dto.setBodyPartList(List.of(bodyPartId));
    }
    if (Objects.nonNull(exerciseTypeId)) {
      dto.setExerciseTypeList(List.of(exerciseTypeId));
    }
    if (Objects.nonNull(equipmentTypeId)) {
      dto.setEquipmentTypeList(List.of(equipmentTypeId));
    }
    dto.setAmountOfExercises(amountOfExercises);
    return dto;
  }
}
