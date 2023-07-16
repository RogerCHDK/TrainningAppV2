package com.rogerfitness.workoutsystem.exceptions.handlers;

import com.rogerfitness.workoutsystem.exceptions.WorkoutSystemApplicationException;
import com.rogerfitness.workoutsystem.factory.ExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
@Slf4j
public class SchedulerErrorHandler implements ErrorHandler {
    private final ExceptionFactory factory;

    public SchedulerErrorHandler(ExceptionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void handleError(Throwable ex) {
        log.error("Error occurred in the scheduler: {}", ex.getMessage(), ex);
        if (ex instanceof WorkoutSystemApplicationException) {
            factory.getExceptionHandler((WorkoutSystemApplicationException) ex).handleException((WorkoutSystemApplicationException) ex);
        }
    }
}
