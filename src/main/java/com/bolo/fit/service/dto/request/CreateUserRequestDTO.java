package com.bolo.fit.service.dto.request;

import com.bolo.fit.enums.RoleEnum;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class CreateUserRequestDTO {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    @Min(6)
    private String password;

    @NotNull
    private RoleEnum role;
}
