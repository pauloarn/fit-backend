package com.bolo.fit.service.dto.response;

import com.bolo.fit.model.Exercise;
import com.bolo.fit.model.ExerciseRoutine;
import com.bolo.fit.model.ExerciseRoutineExercise;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExerciseRoutinePaginatedResponseDTO {
    private Long routineId;
    private String routineName;
    private List<String> exercises;

    public ExerciseRoutinePaginatedResponseDTO (ExerciseRoutine exerciseRoutine) {
        List<String> exercises = new ArrayList<>();
        for (ExerciseRoutineExercise exercise : exerciseRoutine.getExerciseRoutineExercise()) {
            exercises.add(exercise.getExercise().getNome());
            for (Exercise secondaryExercise : exercise.getSecondaryExercisesList()) {
                exercises.add(secondaryExercise.getNome());
            }
        }
        this.routineId = exerciseRoutine.getExerciseRoutineId();
        this.routineName = exerciseRoutine.getName();
        this.exercises = exercises;
    }
}
