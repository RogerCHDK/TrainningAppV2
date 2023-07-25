package com.rogerfitness.workoutsystem.exceptions.user;

import com.rogerfitness.workoutsystem.exceptions.WorkoutSystemApplicationException;

public class CreateUserDatabaseException extends WorkoutSystemApplicationException {
    public CreateUserDatabaseException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
