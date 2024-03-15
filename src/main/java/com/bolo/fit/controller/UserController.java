package com.bolo.fit.controller;

import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.service.UserService;
import com.bolo.fit.service.dto.request.CreateUserRequestDTO;
import com.bolo.fit.service.dto.response.Response;
import com.bolo.fit.service.dto.response.UserDetailResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public Response<UserDetailResponseDTO> createUser(
            @RequestBody @Valid CreateUserRequestDTO createUserRequestDTO
    ) throws ApiErrorException {
        Response<UserDetailResponseDTO> response = new Response<>();
        response.setData(userService.createUser(createUserRequestDTO));
        return response.setOk();
    }
}
