package com.bolo.fit.service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosExercicioPaginacaoDTO {

    private Integer page;
    private Integer sizePage;
    private Boolean getImageBase64 = false;
    private String searchText;
    private Long bodyPartId;
    private Long equipmentTypeId;
    private Long exerciseTypeId;
}
