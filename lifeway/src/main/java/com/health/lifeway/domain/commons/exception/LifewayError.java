package com.health.lifeway.domain.commons.exception;

public class LifewayError {

    private String code;
    private String message;

    public LifewayError() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFormattedMessage() {
        return String.format("%s - %s", getCode(), getMessage());
    }

    public static class Builder {

        private final LifewayError lifewayError;

        public Builder(String code, String message) {
            this.lifewayError = new LifewayError();
            lifewayError.code = code;
            lifewayError.message = message;
        }

        public LifewayError build() {
            return lifewayError;
        }
    }
}
