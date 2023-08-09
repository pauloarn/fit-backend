package com.bolo.fit.service.dto.response;

import com.bolo.fit.enums.RoleEnum;
import com.bolo.fit.model.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailResponseDTO {
    private String name;
    private String email;
    private RoleEnum role;

    public UserDetailResponseDTO(Users user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
