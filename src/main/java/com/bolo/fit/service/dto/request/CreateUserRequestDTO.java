package com.bolo.fit.service.dto.request;

import com.bolo.fit.enums.RoleEnum;
import lombok.Getter;

@Getter
public class CreateUserRequestDTO {
    private String email;
    private String name;
    private String password;
    private RoleEnum role;
}
