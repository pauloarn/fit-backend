package com.bolo.fit.service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseStatsBaseResponseDTO {

    private String name;
    private String nome;
    private Integer exercises;
    private LocalDateTime createdAt;
    private Long id;
}
