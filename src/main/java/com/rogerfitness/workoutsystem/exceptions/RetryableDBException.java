package com.rogerfitness.workoutsystem.exceptions;

import lombok.Getter;

@Getter
public class RetryableDBException extends DBException{
    private final String errorCode;

    public RetryableDBException() {
        this.errorCode = null;
    }

    public RetryableDBException(String message) {
        super(message);
        this.errorCode = null;
    }

    public RetryableDBException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    public RetryableDBException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public RetryableDBException(Throwable cause) {
        super(cause);
        this.errorCode = null;
    }
}
