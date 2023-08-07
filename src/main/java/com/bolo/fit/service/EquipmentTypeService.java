package com.bolo.fit.service;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.model.EquipmentType;
import com.bolo.fit.repository.EquipmentTypeRepository;
import com.bolo.fit.service.dto.response.EquipmentTypeResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class EquipmentTypeService extends AbstractServiceRepo<EquipmentTypeRepository, EquipmentType, Long>{
    public EquipmentTypeService(EquipmentTypeRepository repository) {
        super(repository);
    }
    public List<EquipmentTypeResponseDTO> getAllEquipmentTypes() {
        List<EquipmentType> equipmentTypes = repository.findAll();
        log.info("Searching all body parts");
        return equipmentTypes.stream().map((et)-> new EquipmentTypeResponseDTO().fromEquipmentType(et)).collect(Collectors.toList());
    }

    public EquipmentType getExerciseTypeById(Long exerciseTypeId) throws ApiErrorException {
        return repository.findById(exerciseTypeId).orElseThrow(()-> new ApiErrorException(HttpStatus.BAD_REQUEST, MessageEnum.EQUIPENT_TYPE_NOT_FOUND));
    }
}
