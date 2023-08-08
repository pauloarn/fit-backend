package com.bolo.fit.service.dto.request;

import lombok.Getter;

@Getter
public class CreateSessionRequestDTO {
    private String userEmail;
    private String userPassword;
}
