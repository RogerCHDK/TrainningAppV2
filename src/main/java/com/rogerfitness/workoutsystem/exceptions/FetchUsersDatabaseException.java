package com.rogerfitness.workoutsystem.exceptions;

public class FetchUsersDatabaseException extends WorkoutSystemApplicationException{
    public FetchUsersDatabaseException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
