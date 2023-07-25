package com.rogerfitness.workoutsystem.exceptions.user;

import com.rogerfitness.workoutsystem.exceptions.WorkoutSystemApplicationException;

public class FetchUsersDatabaseException extends WorkoutSystemApplicationException {
    public FetchUsersDatabaseException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
