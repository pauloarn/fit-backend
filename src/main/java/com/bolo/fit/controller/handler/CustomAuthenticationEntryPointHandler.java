package com.bolo.fit.controller.handler;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.service.dto.response.Response;
import com.bolo.fit.utils.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final JsonUtils jsonUtils;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Response<String> response = new Response<>();

        response.setStatusCode(HttpStatus.UNAUTHORIZED, MessageEnum.ACESSO_NEGADO);

        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().print(jsonUtils.objectToJson(response));
    }
}
