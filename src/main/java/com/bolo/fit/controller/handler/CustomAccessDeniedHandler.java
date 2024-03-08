package com.bolo.fit.controller.handler;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.service.dto.response.Response;
import com.bolo.fit.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final JsonUtils jsonUtils;

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
    Response<String> response = new Response<>();

    response.setStatusCode(HttpStatus.UNAUTHORIZED, MessageEnum.ACESSO_NEGADO);

    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    httpServletResponse.setContentType("application/json");
    httpServletResponse.getWriter().print(jsonUtils.objectToJson(response));
  }
}
