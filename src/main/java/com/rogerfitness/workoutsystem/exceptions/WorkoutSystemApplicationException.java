package com.rogerfitness.workoutsystem.exceptions;

import lombok.Getter;

@Getter
public class WorkoutSystemApplicationException extends Exception {
    private final String errorCode;

    public WorkoutSystemApplicationException() {
        this.errorCode = null;
    }

    public WorkoutSystemApplicationException(Throwable cause) {
        super(cause);
        this.errorCode = null;
    }

    public WorkoutSystemApplicationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    public WorkoutSystemApplicationException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
