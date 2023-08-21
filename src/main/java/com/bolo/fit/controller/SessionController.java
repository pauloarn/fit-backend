package com.bolo.fit.controller;

import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.service.AuthorizationService;
import com.bolo.fit.service.dto.request.CreateSessionRequestDTO;
import com.bolo.fit.service.dto.response.Response;
import com.bolo.fit.service.dto.response.SessionResponseDTO;
import com.bolo.fit.service.dto.response.UserDetailResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("session")
public class SessionController {
    @Autowired
    AuthorizationService authorizationService;
    @PostMapping()
    public Response<SessionResponseDTO> createSession(
            @RequestBody @Valid CreateSessionRequestDTO createSessionRequestDTO
    ) throws ApiErrorException {
        Response<SessionResponseDTO> response = new Response<>();
        response.setData(authorizationService.createSession(createSessionRequestDTO ));
        return response.setOk();
    }

    @GetMapping("/me")
    public Response<UserDetailResponseDTO> getMe(){
        Response<UserDetailResponseDTO> response = new Response<>();
        response.setData(authorizationService.getLoggerUserDTO());
        response.setOk();
        return response;
    }
}
