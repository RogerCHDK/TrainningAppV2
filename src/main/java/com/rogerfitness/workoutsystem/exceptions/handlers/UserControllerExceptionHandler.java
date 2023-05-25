package com.rogerfitness.workoutsystem.exceptions.handlers;

import com.rogerfitness.workoutsystem.constants.ErrorConstants;
import com.rogerfitness.workoutsystem.controllers.UserController;
import com.rogerfitness.workoutsystem.exceptions.Error;
import com.rogerfitness.workoutsystem.exceptions.FetchUsersDatabaseException;
import com.rogerfitness.workoutsystem.responses.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice(assignableTypes = {UserController.class})
public class UserControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex){
        log.error("Error attempting to complete service request :: {} :: {}", ex.getClass().getName(), ex.getMessage(), ex);
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder().build();
        if (ex instanceof FetchUsersDatabaseException){
            String exceptionMessage = Objects.nonNull(ex.getCause()) ? ex.getCause().toString() : ex.toString();
//            String reason = String.format("%s - Database exception occurred while fetching users");
//            TODO: Add kafka to send alert to a topic
        }
        apiErrorResponse = apiErrorResponse.toBuilder()
                .error(Error.builder().code(ErrorConstants.INTERNAL_SERVER_ERROR).description("General Error").build())
                .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
