package com.rogerfitness.workoutsystem.exceptions;

import lombok.Getter;

@Getter
public class NonRetryableDBException extends DBException{

    private final String errorCode;
    public NonRetryableDBException() {
        this.errorCode = null;
    }

    public NonRetryableDBException(String message) {
        super(message);
        this.errorCode = null;
    }

    public NonRetryableDBException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    public NonRetryableDBException(Throwable cause) {
        super(cause);
        this.errorCode = null;
    }

    public NonRetryableDBException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
