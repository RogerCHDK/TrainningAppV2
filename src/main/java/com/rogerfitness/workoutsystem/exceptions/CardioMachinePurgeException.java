package com.rogerfitness.workoutsystem.exceptions;

public class CardioMachinePurgeException extends WorkoutSystemApplicationException{
    public CardioMachinePurgeException(String message, Throwable cause, String errorCode) {
        super(message, cause);
    }
}
