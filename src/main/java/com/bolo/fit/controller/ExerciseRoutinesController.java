package com.bolo.fit.controller;

import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.service.ExerciseRoutineService;
import com.bolo.fit.service.dto.request.CreateExerciseRoutineRequestDTO;
import com.bolo.fit.service.dto.request.CreateRandomExerciseRoutineRequestDTO;
import com.bolo.fit.service.dto.request.DadosExercicioPaginacaoDTO;
import com.bolo.fit.service.dto.request.UpdateExerciseRoutineRequestDTO;
import com.bolo.fit.service.dto.response.ExerciseRoutinePaginatedResponseDTO;
import com.bolo.fit.service.dto.response.ExerciseRoutineResponseDTO;
import com.bolo.fit.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercise-routine")
public class ExerciseRoutinesController {

    private final ExerciseRoutineService exerciseRoutineService;
    @GetMapping()
    private Response<Page<ExerciseRoutinePaginatedResponseDTO>> listExerciseRoutines(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) throws IOException {
        Response<Page<ExerciseRoutinePaginatedResponseDTO>> response = new Response<>();
        DadosExercicioPaginacaoDTO paginacaoRequestData = new DadosExercicioPaginacaoDTO();
        paginacaoRequestData.setPage(page);
        paginacaoRequestData.setSizePage(size);
        paginacaoRequestData.setGetImageBase64(false);
        response.setOk().setData(exerciseRoutineService.getAllRoutines(paginacaoRequestData));
        return response;
    }

    @GetMapping("/{id}")
    private Response<ExerciseRoutineResponseDTO> getExerciseRoutineDetail(
            @PathVariable("id") Long exerciseRoutineId
    ) throws ApiErrorException {
        Response<ExerciseRoutineResponseDTO> response = new Response<>();
        response.setOk().setData(exerciseRoutineService.getExerciseRoutineDetail(exerciseRoutineId));
        return response;
    }
    @PutMapping("/{id}/update")
    private Response<ExerciseRoutineResponseDTO> updateExerciseRoutine(
            @PathVariable("id") Long exerciseRoutineId,
            @RequestBody UpdateExerciseRoutineRequestDTO updateExerciseRoutineRequest
    ) throws ApiErrorException {
        Response<ExerciseRoutineResponseDTO> response = new Response<>();
        response.setOk().setData(exerciseRoutineService.updateExerciseRoutine(exerciseRoutineId, updateExerciseRoutineRequest));
        return response;
    }

    @PostMapping("/random")
    private Response<ExerciseRoutineResponseDTO> createRandomExerciseRoutine(
            @RequestBody CreateRandomExerciseRoutineRequestDTO createRandomExerciseRoutineRequest
    ) throws ApiErrorException {
        Response<ExerciseRoutineResponseDTO> response = new Response<>();
        response.setOk().setData(exerciseRoutineService.generateRandomRoutine(createRandomExerciseRoutineRequest));
        return response;
    }

    @PostMapping()
    private Response<ExerciseRoutineResponseDTO> createExerciseRoutine(
            @RequestBody CreateExerciseRoutineRequestDTO createExerciseRoutineRequest
    ) throws ApiErrorException {
        Response<ExerciseRoutineResponseDTO> response = new Response<>();
        response.setOk().setData(exerciseRoutineService.createExerciseRoutine(createExerciseRoutineRequest));
        return response;
    }
}
