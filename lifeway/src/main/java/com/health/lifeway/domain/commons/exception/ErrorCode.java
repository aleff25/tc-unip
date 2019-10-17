package com.health.lifeway.domain.commons.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "LIFEWAY-ERROR-404"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "LIFEWAY-ERROR-400");

    private final int httpCode;
    private final String code;

    ErrorCode(int httpCode, String code) {
        this.httpCode = httpCode;
        this.code = code;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getCode() {
        return code;
    }
}
