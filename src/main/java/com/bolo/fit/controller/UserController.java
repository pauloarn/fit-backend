package com.bolo.fit.controller;

import com.bolo.fit.service.dto.request.CreateUserRequestDTO;
import com.bolo.fit.service.dto.response.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    @PostMapping()
    public Response<String> createUser(
            @RequestBody @Valid CreateUserRequestDTO createUserRequestDTO
    ){
        Response<String> response = new Response<>();

        return response.setOk();
    }
}
