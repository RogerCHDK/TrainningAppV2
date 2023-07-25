package com.rogerfitness.workoutsystem.exceptions;

public class CreateUserDatabaseException extends WorkoutSystemApplicationException {
    public CreateUserDatabaseException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
