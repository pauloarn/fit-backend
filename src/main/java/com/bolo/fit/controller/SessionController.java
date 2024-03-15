package com.bolo.fit.controller;

import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.service.AuthorizationService;
import com.bolo.fit.service.dto.request.CreateSessionRequestDTO;
import com.bolo.fit.service.dto.response.Response;
import com.bolo.fit.service.dto.response.SessionResponseDTO;
import com.bolo.fit.service.dto.response.UserDetailResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("session")
@RequiredArgsConstructor
public class SessionController {
    private final AuthorizationService authorizationService;

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
