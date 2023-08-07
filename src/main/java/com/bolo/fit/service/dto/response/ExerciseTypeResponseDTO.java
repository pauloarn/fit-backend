package com.bolo.fit.service.dto.response;

import com.bolo.fit.model.ExerciseType;

public class ExerciseTypeResponseDTO extends BaseStatsBaseResponseDTO{

    public ExerciseTypeResponseDTO fromExerciseType(ExerciseType exerciseType) {
        ExerciseTypeResponseDTO bp = new ExerciseTypeResponseDTO();
        bp.setName(exerciseType.getName());
        bp.setNome(exerciseType.getNome());
        bp.setId(exerciseType.getExeciseId());
        bp.setCreatedAt(exerciseType.getCreatedAt());
        bp.setExercises(exerciseType.getExercise().size());
        return bp;
    }
}
