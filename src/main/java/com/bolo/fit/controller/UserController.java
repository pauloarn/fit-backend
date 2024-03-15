package com.bolo.fit.controller;

import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.service.UserService;
import com.bolo.fit.service.dto.request.CreateUserRequestDTO;
import com.bolo.fit.service.dto.response.Response;
import com.bolo.fit.service.dto.response.UserDetailResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public Response<UserDetailResponseDTO> getLoggedUserData(
    ){
        Response<UserDetailResponseDTO> response = new Response<>();
        response.setOk();
        response.setData(userService.getUserInfo());
        return response;
    }

    @PostMapping()
    public Response<UserDetailResponseDTO> createUser(
            @RequestBody @Valid CreateUserRequestDTO createUserRequestDTO
    ) throws ApiErrorException {
        Response<UserDetailResponseDTO> response = new Response<>();
        response.setData(userService.createUser(createUserRequestDTO));
        return response.setOk();
    }
}
