package com.bolo.fit.controller;

import com.bolo.fit.service.ExerciseTypeService;
import com.bolo.fit.service.dto.response.ExerciseTypeResponseDTO;
import com.bolo.fit.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercise-type")
public class ExerciseTypeController {

    private final ExerciseTypeService exerciseTypeService;

    @GetMapping()
    private Response<List<ExerciseTypeResponseDTO>> listaBodyParts() {
        Response<List<ExerciseTypeResponseDTO>> response = new Response<>();
        response.setOk().setData(exerciseTypeService.getAllExerciseTypes());
        return response;
    }
}
