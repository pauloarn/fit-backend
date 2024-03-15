package com.bolo.fit.service.dto.request;

import com.bolo.fit.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequestDTO {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    @Size(min=6)
    private String password;

    @NotNull
    private RoleEnum role;
}
