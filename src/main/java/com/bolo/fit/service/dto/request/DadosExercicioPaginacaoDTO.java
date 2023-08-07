package com.bolo.fit.service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosExercicioPaginacaoDTO {

    private Integer page;
    private Integer sizePage;
    private Boolean getImageBase64 = false;
}
