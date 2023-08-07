package com.bolo.fit.enums;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public enum MessageEnum {
    REQUISICAO_CONCLUIDA("message.api.requisicao_concluida"),
    EXERCISE_NOT_FOUND("message.api.exercise.not.found"),
    EXERCISE_TYPE_NOT_FOUND("message.api.exercise.type.not.found"),
    BODY_PART_NOT_FOUND("message.api.body.part.not.found"),
    EQUIPENT_TYPE_NOT_FOUND("message.api.equipment.type.not.found"),
    ROUTINE_NOT_FOUND("message.api.routine.not.found"),
    UNKNOWN_ERROR("message.api.error.desconhecido");

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
