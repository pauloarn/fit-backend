package com.bolo.fit.service.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class CreateSessionRequestDTO {
    @Email
    @NotNull
    private String userEmail;

    @NotNull
    @Min(6)
    private String userPassword;
}
