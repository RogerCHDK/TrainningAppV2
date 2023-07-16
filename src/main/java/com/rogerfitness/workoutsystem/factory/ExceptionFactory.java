package com.rogerfitness.workoutsystem.factory;

import com.rogerfitness.workoutsystem.exceptions.CardioMachinePurgeException;
import com.rogerfitness.workoutsystem.exceptions.ExceptionHandler;
import com.rogerfitness.workoutsystem.exceptions.WorkoutSystemApplicationException;
import com.rogerfitness.workoutsystem.exceptions.handlers.CardioMachineExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ExceptionFactory {
    private final Map<Class<?>, ExceptionHandler> exceptionHandlerMap = new HashMap<>();
    private final CardioMachineExceptionHandler cardioMachineExceptionHandler;

    public ExceptionFactory(CardioMachineExceptionHandler cardioMachineExceptionHandler) {
        this.cardioMachineExceptionHandler = cardioMachineExceptionHandler;
    }

    @PostConstruct
    public void registerHandlers() {
        exceptionHandlerMap.put(CardioMachinePurgeException.class, cardioMachineExceptionHandler);
        log.info("register exception factory");
    }

    public ExceptionHandler getExceptionHandler(WorkoutSystemApplicationException ex) {
        Optional<ExceptionHandler> handler = Objects.nonNull(ex) ? Optional.of(exceptionHandlerMap.get(ex.getClass())) : Optional.empty();
        if (handler.isPresent()) {
            return handler.get();
        } else {
            throw new IllegalStateException("Invalid arguments");
        }
    }
}
