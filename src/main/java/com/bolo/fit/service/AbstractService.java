package com.bolo.fit.service;

import com.bolo.fit.enums.MessageEnum;
import com.bolo.fit.exceptions.ApiErrorException;
import com.bolo.fit.utils.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import static com.bolo.fit.utils.ExceptionUtils.logException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;
import java.util.function.Supplier;

@Log4j2
public abstract class AbstractService {


    @Autowired
    public ApplicationEventPublisher publisher;

    private JsonUtils jsonUtils;

    @PersistenceContext
    public EntityManager em;

    @Autowired
    public final void setJsonUtils(JsonUtils jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    protected void throwBadRequest(MessageEnum messageEnum) throws ApiErrorException {
        throw ApiErrorException.builder()
                .badRequest()
                .messageEnum(messageEnum)
                .build();
    }

    protected void throwNotFound(MessageEnum messageEnum) throws ApiErrorException {
        throw ApiErrorException.builder()
                .notFound()
                .messageEnum(messageEnum)
                .build();
    }

    protected void throwUnprocessableEntity(MessageEnum messageEnum) throws ApiErrorException {
        throw ApiErrorException.builder()
                .unprocessabelEntity()
                .messageEnum(messageEnum)
                .build();
    }

    protected void throwInternalErrorServer(MessageEnum messageEnum) throws ApiErrorException {
        throw ApiErrorException.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .messageEnum(messageEnum)
                .build();
    }

    protected Supplier<ApiErrorException> supplierThrowUnprocessableEntity(MessageEnum messageEnum) {
        return () -> ApiErrorException.builder()
                .unprocessabelEntity()
                .messageEnum(messageEnum)
                .build();
    }

    protected Supplier<ApiErrorException> supplierThrowNotFound(MessageEnum messageEnum) {
        return () -> ApiErrorException.builder()
                .notFound()
                .messageEnum(messageEnum)
                .build();
    }

    protected Supplier<ApiErrorException> supplierThrowApiErrorException(MessageEnum messageEnum, HttpStatus httpStatus) {
        return () -> ApiErrorException.builder()
                .httpStatus(httpStatus)
                .messageEnum(messageEnum)
                .build();
    }

    public Pageable generatePageable(Integer page, Integer size) {
        if (page == null || size == null)
            return null;
        return generatePageable(page, size, Sort.unsorted());
    }

    public Pageable generatePageable(Integer page, Integer size, Sort sort) {
        if (page == null || size == null)
            return null;
        if (page - 1 > -1) page = page - 1;
        return PageRequest.of(page, size, sort);
    }

    public boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    public boolean isNotNull(Object obj) {
        return Objects.nonNull(obj);
    }

    public boolean isEquals(Object objectLeft, Object objectRight) {
        return Objects.equals(objectLeft, objectRight);
    }

    public boolean isNotEquals(Object objectLeft, Object objectRight) {
        return !this.isEquals(objectLeft, objectRight);
    }

    public void logDto(String msg, Object dto) {
        try {
            log.debug(msg + ": {}", jsonUtils.objectToJson(dto));
        } catch (Exception e) {
            log.debug(msg + ": {}", dto);
            log.warn("Ocorreu um problema ao mostrar o dto em json: {}", dto.getClass().getSimpleName());
            logException(e);
        }
    }

    public void logDto(Object dto) {
        var className = dto.getClass().getSimpleName();
        this.logDto(String.format("Dados de %s", className), dto);
    }
}
