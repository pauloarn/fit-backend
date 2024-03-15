package com.bolo.fit.service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
public class CreateSessionRequestDTO {
    @Email
    @NotNull
    private String userEmail;

    @NotNull
    @Size(min=6)
    private String userPassword;
}
