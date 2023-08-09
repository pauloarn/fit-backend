package com.bolo.fit.controller;

import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.service.AuthorizationService;
import com.bolo.fit.service.dto.request.CreateSessionRequestDTO;
import com.bolo.fit.service.dto.response.Response;
import com.bolo.fit.service.dto.response.SessionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
