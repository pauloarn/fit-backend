package com.bolo.fit.controller;

import com.bolo.fit.service.AuthorizationService;
import com.bolo.fit.service.dto.request.CreateSessionRequestDTO;
import com.bolo.fit.service.dto.response.Response;
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
    public Response<String> createSession(
            @RequestBody @Valid CreateSessionRequestDTO createSessionRequestDTO
    ){
        Response<String> response = new Response<>();
        authorizationService.createSession(createSessionRequestDTO );
        return response.setOk();
    }

}
