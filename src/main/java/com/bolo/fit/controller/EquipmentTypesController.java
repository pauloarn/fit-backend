package com.bolo.fit.controller;

import com.bolo.fit.service.EquipmentTypeService;
import com.bolo.fit.service.dto.response.EquipmentTypeResponseDTO;
import com.bolo.fit.service.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipment-type")
public class EquipmentTypesController {

    private final EquipmentTypeService equipmentTypeService;
    @GetMapping()
    private Response<List<EquipmentTypeResponseDTO>> listaBodyParts() {
        Response<List<EquipmentTypeResponseDTO>> response = new Response<>();
        response.setOk().setData(equipmentTypeService.getAllEquipmentTypes());
        return response;
    }

}
