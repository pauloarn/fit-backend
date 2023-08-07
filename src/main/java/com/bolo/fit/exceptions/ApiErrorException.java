package com.bolo.fit.exceptions;

import com.bolo.fit.enums.MessageEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ApiErrorException extends Exception {

    private MessageEnum messageApiEnum = MessageEnum.UNKNOWN_ERROR;
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private Object errorMsg = null;
    protected String[] args = null;

    public ApiErrorException(HttpStatus httpStatus, MessageEnum messageApiEnum, List<String> args) {
        super(messageApiEnum.getMessage(args.toArray(new String[0])));
        this.messageApiEnum = messageApiEnum;
        this.httpStatus = httpStatus;
        this.args = args.toArray(new String[0]);
    }

    public ApiErrorException(HttpStatus httpStatus, MessageEnum messageApiEnum) {
        super(messageApiEnum.getMessage());
        this.messageApiEnum = messageApiEnum;
        this.httpStatus = httpStatus;
    }

    public ApiErrorException(HttpStatus httpStatus, MessageEnum messageApiEnum, Object errorMsg) {
        super(messageApiEnum.getMessage());
        this.messageApiEnum = messageApiEnum;
        this.httpStatus = httpStatus;
        this.errorMsg = errorMsg;
    }

    public static Builder builder() {
        return new ApiErrorException.Builder();
    }

    public static class Builder {
        private final ApiErrorException apiErrorException;
        private final List<String> args = new ArrayList<>();

        public Builder() {
            this.apiErrorException = new ApiErrorException();
        }

        public Builder httpStatus(HttpStatus httpStatus) {
            apiErrorException.httpStatus = httpStatus;
            return this;
        }

        public Builder messageEnum(MessageEnum messageEnum) {
            this.apiErrorException.messageApiEnum = messageEnum;
            return this;
        }

        public Builder errorMsg(String errorMsg) {
            this.apiErrorException.errorMsg = errorMsg;
            return this;
        }

        public Builder badRequest() {
            this.apiErrorException.httpStatus = HttpStatus.BAD_REQUEST;
            return this;
        }

        public Builder unprocessabelEntity() {
            this.apiErrorException.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            return this;
        }

        public Builder notFound() {
            this.apiErrorException.httpStatus = HttpStatus.NOT_FOUND;
            return this;
        }

        public Builder addArgs(String msg) {
            this.args.add(msg);
            return this;
        }

        public ApiErrorException build() {
            this.apiErrorException.args = args.toArray(new String[0]);
            return this.apiErrorException;
        }
    }
}
