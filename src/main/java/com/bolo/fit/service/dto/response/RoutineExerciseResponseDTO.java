package com.bolo.fit.service.dto.response;

import com.bolo.fit.model.Exercise;
import com.bolo.fit.model.ExerciseRoutineExercise;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoutineExerciseResponseDTO {
    private Long routineExerciseId;
    private Integer series;
    private Integer repetitions;
    private String observation;
    private Double exerciseWeight;
    private Double restTime;
    private ExerciseResponseDTO execise;
    private List<ExerciseResponseDTO> biSetExercises;
    public RoutineExerciseResponseDTO (ExerciseRoutineExercise routineExercise, Boolean shouldGetImageData) throws IOException {
        this.routineExerciseId = routineExercise.getExerciseRoutineId();
        this.series = routineExercise.getSeries();
        this.repetitions = routineExercise.getRepetitions();
        this.observation = routineExercise.getNotes();
        this.restTime = routineExercise.getRest_time();
        this.exerciseWeight = routineExercise.getExerciseWeight();
        this.execise = new ExerciseResponseDTO(routineExercise.getExercise(), shouldGetImageData);
        List<ExerciseResponseDTO> listAux = new ArrayList<>();
        for(Exercise ex: routineExercise.getSecondaryExercisesList()){
            listAux.add(new ExerciseResponseDTO(ex, true));
        }
        this.biSetExercises = listAux;
    }
}
