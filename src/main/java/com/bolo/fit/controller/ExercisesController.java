package com.bolo.fit.controller;

import com.bolo.fit.service.ExerciseService;
import com.bolo.fit.service.dto.request.DadosExercicioPaginacaoDTO;
import com.bolo.fit.service.dto.response.ExerciseResponseDTO;
import com.bolo.fit.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercises")
public class ExercisesController {

    private final ExerciseService exerciseService;

    @GetMapping()
    private Response<Page<ExerciseResponseDTO>> listaExercicios(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "getBase64", defaultValue = "false") String getBase64
    ) throws IOException {
        Response<Page<ExerciseResponseDTO>> response = new Response<>();
        DadosExercicioPaginacaoDTO paginacaoRequestData = new DadosExercicioPaginacaoDTO();
        paginacaoRequestData.setPage(page);
        paginacaoRequestData.setSizePage(size);
        paginacaoRequestData.setGetImageBase64(getBase64.equals("true"));
        response.setOk().setData(exerciseService.buscaTodosExercicios(paginacaoRequestData));
        return response;
    }

}
