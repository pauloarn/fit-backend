package com.bolo.fit.enums;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public enum MessageEnum {
    REQUISICAO_CONCLUIDA("message.api.requisicao_concluida"),
    EXERCISE_NOT_FOUND("message.api.exercise.not.found"),
    ROUTE_NOT_FOUND("message.api.rout.not.found"),
    ENDPOINT_ERROR("message.api.endpoint.error"),
    VALIDATION_ERROR("message.api.validacao.error"),
    METHOD_NOT_SUPPORTED("message.api.method.not.supported"),
    MISSING_REQUEST_PARAMETER("message.api.missing.request.parameter"),
    EXERCISE_TYPE_NOT_FOUND("message.api.exercise.type.not.found"),
    BODY_PART_NOT_FOUND("message.api.body.part.not.found"),
    USER_ALREADY_EXISTS("message.api.use.already.exists"),
    USER_NOT_FOUND("message.api.user.not.found"),
    EQUIPENT_TYPE_NOT_FOUND("message.api.equipment.type.not.found"),
    ROUTINE_NOT_FOUND("message.api.routine.not.found"),
    UNKNOWN_ERROR("message.api.error.desconhecido"),

    INVALID_TOKEN("message.api.invalid.token"),
    FAIL_TO_CREATE_TOKEN("message.api.fail.to.create.token"),;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    private String description;

    MessageEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getMessage(String... args) {
        String msg = convertToUTF8(resourceBundle.getString(this.description));
        if (msg.contains("�")) {
            msg = resourceBundle.getString(this.description);
        }
        return args == null ? msg : MessageFormat.format(msg, args);
    }

    private static String convertToUTF8(String message) {
        try {
            return new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return message;
        }
    }
}
