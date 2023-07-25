package com.rogerfitness.workoutsystem.exceptions.handlers;

import com.rogerfitness.workoutsystem.exceptions.WorkoutSystemApplicationException;

public interface ExceptionHandler {
    void handleException(WorkoutSystemApplicationException ex);
}
