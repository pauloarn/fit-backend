package com.bolo.fit.service.dto.response;

import com.bolo.fit.model.BodyPart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BodyPartResponseDTO extends BaseStatsBaseResponseDTO{
    public BodyPartResponseDTO fromBodyPart(BodyPart bodyPart) {
        BodyPartResponseDTO bp = new BodyPartResponseDTO();
        bp.setName(bodyPart.getName());
        bp.setNome(bodyPart.getNome());
        bp.setId(bodyPart.getBodyPartId());
        bp.setCreatedAt(bodyPart.getCreatedAt());
        bp.setExercises(bodyPart.getExercise().size());
        return bp;
    }
}
