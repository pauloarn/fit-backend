package com.bolo.fit.controller.handler;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.service.dto.response.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.bolo.fit.utils.ExceptionUtils.logException;
import static java.lang.String.format;
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Log4j2
public class ApiExceptionHandler extends ResponseEntityExceptionHandler  {
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Response<String> response = new Response<>();
        log.error("Rota não encontrada");
        response.setStatusCode(status, MessageEnum.ROUTE_NOT_FOUND);
        response.setData(format("Rota %s não encontrada!", request.getDescription(false).substring(4)));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Response<String> response = new Response<>();
        response.setStatusCode(status, MessageEnum.MISSING_REQUEST_PARAMETER);
        response.setData(format("Parâmetro obrigatório: %s", ex.getParameterName()));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Response<String> response = new Response<>();
        response.setStatusCode(status, MessageEnum.ENDPOINT_ERROR);
        response.setData(format("Error na conversão do parâmetro [ %s ]: %s", ex.getPropertyName(), ex.getMessage()));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Response<String> response = new Response<>();
        response.setStatusCode(status, MessageEnum.UNKNOWN_ERROR);
        response.setData(ex.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        log.error("Ocorreu um erro de validação");
        log.error(ex);
        logException(ex);
        Response<Map<String, String>> response = new Response<>();
        Map<String, String> errors = new HashMap<>();
        response.setStatusCode(status, MessageEnum.VALIDATION_ERROR);
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        response.setData(errors);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(
                " não suportado para está rota. Métodos suportados são:  ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(", "));

        Response<String> response = new Response<>();
        response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED, MessageEnum.METHOD_NOT_SUPPORTED);
        response.setData(builder.substring(0, builder.length() - 2));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<Object> apiErrorException(ApiErrorException exception) {
        Response<Object> response = new Response<>();
        response.setStatusCode(
                exception.getHttpStatus(),
                exception.getMessageApiEnum().name(),
                exception.getMessageApiEnum().getMessage(exception.getArgs())
        );
        response.setData(exception.getErrorMsg());
        log.error("Um erro foi disparado!!!");
        log.error("[ {} ] :: {}", exception.getMessageApiEnum(), response.getMessage());
        logException(exception);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> allException(Exception exception) {
        Response<String> response = new Response<>();
        log.error("Um erro interno ocorreu!!!");
        logException(exception);
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR, MessageEnum.UNKNOWN_ERROR);
        response.setData(exception.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
