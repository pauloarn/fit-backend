package com.bolo.fit.controller;

import com.bolo.fit.service.ExerciseImageService;
import com.bolo.fit.service.ExerciseService;
import com.bolo.fit.service.dto.request.DadosExercicioPaginacaoDTO;
import com.bolo.fit.service.dto.response.ExerciseResponseDTO;
import com.bolo.fit.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercises")
public class ExercisesController {

    private final ExerciseService exerciseService;
    private final ExerciseImageService exerciseImageService;

    @GetMapping()
    private Response<Page<ExerciseResponseDTO>> listaExercicios(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "searchText", required = false) String searchText,
            @RequestParam(value="bodyPartId", required = false) Long bodyPartId,
            @RequestParam(value="equipmentTypeId", required = false) Long equipmentTypeId,
            @RequestParam(value="exerciseTypeId", required = false) Long exerciseTypeId
    ) throws IOException {
        Response<Page<ExerciseResponseDTO>> response = new Response<>();
        DadosExercicioPaginacaoDTO paginacaoRequestData = new DadosExercicioPaginacaoDTO();
        paginacaoRequestData.setPage(page);
        paginacaoRequestData.setSizePage(size);
        paginacaoRequestData.setBodyPartId(bodyPartId);
        paginacaoRequestData.setExerciseTypeId(exerciseTypeId);
        paginacaoRequestData.setEquipmentTypeId(equipmentTypeId);
        if(Objects.nonNull(searchText) && !searchText.isBlank()){
            paginacaoRequestData.setSearchText(searchText);
        }
        response.setOk().setData(exerciseService.buscaTodosExercicios(paginacaoRequestData));
        return response;
    }

    @GetMapping("/image")
    public Response<String> getExerciseImage(
            @RequestParam(value = "imgUrl") String imgUrl
    ) {
        var response = new Response<String>();

        response.setOk().setData(exerciseImageService.getExerciseImage(imgUrl));

        return response;
    }
}
