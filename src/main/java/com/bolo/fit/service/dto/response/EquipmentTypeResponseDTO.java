package com.bolo.fit.service.dto.response;

import com.bolo.fit.model.EquipmentType;

public class EquipmentTypeResponseDTO extends BaseStatsBaseResponseDTO{

    public EquipmentTypeResponseDTO fromEquipmentType(EquipmentType equipmentType) {
        EquipmentTypeResponseDTO bp = new EquipmentTypeResponseDTO();
        bp.setName(equipmentType.getName());
        bp.setNome(equipmentType.getNome());
        bp.setId(equipmentType.getEquipmentTypeId());
        bp.setCreatedAt(equipmentType.getCreatedAt());
        bp.setExercises(equipmentType.getExercise().size());
        return bp;
    }
}
