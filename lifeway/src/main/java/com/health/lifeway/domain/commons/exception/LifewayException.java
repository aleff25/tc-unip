package com.health.lifeway.domain.commons.exception;

public class LifewayException extends RuntimeException {

    private final LifewayError lifewayError;
    private final int httpStatusCode;

    public LifewayException(String code, String message, int httpStatusCode) {
        this(new LifewayError.Builder(code, message).build(), httpStatusCode);
    }

    public LifewayException(LifewayError error, int httpStatusCode) {
        super(error.getFormattedMessage());
        this.lifewayError = error;
        this.httpStatusCode = httpStatusCode;
    }

    public LifewayException(LifewayError error, Throwable ex, int httpStatusCode) {
        super(error.getFormattedMessage(), ex);
        this.lifewayError = error;
        this.httpStatusCode = httpStatusCode;
    }

    public LifewayError getLifewayError() {
        return lifewayError;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
