package com.bolo.fit.service.dto.response;

import com.bolo.fit.model.Exercise;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class ExerciseResponseDTO {
    private Long exerciseId;
    private String nome;
    private String name;
    private ImageBase64ResponseDTO imageData;

    public ExerciseResponseDTO(Exercise exercise, Boolean shouldGetGifData) throws IOException {
        this.exerciseId = exercise.getExeciseId();
        this.nome = exercise.getNome();
        this.name = exercise.getName();
        if(shouldGetGifData){
            this.imageData = exercise.getExerciseGifData();
        }
    }
}
