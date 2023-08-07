package com.bolo.fit.controller;

import com.bolo.fit.service.BodyPartService;
import com.bolo.fit.service.dto.response.BodyPartResponseDTO;
import com.bolo.fit.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/body-part")
public class BodyPartsController {

    private final BodyPartService bodyPartService;

    @GetMapping()
    private Response<List<BodyPartResponseDTO>> listaBodyParts() {
        Response<List<BodyPartResponseDTO>> response = new Response<>();
        response.setOk().setData(bodyPartService.getAllBodyParts());
        return response;
    }
}
