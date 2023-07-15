package com.rogerfitness.workoutsystem.exceptions.handlers;

import com.rogerfitness.workoutsystem.constants.AlertConstants;
import com.rogerfitness.workoutsystem.constants.ErrorConstants;
import com.rogerfitness.workoutsystem.controllers.UserController;
import com.rogerfitness.workoutsystem.dto.SirenAlertMessage;
import com.rogerfitness.workoutsystem.exceptions.Error;
import com.rogerfitness.workoutsystem.exceptions.FetchUsersDatabaseException;
import com.rogerfitness.workoutsystem.messaging.publishers.SirenAlertPublisher;
import com.rogerfitness.workoutsystem.responses.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.rogerfitness.workoutsystem.constants.WorkoutSystemApplicationConstants.APP_NAME;

@Slf4j
@ControllerAdvice(assignableTypes = {UserController.class})
public class UserControllerExceptionHandler {
    private final SirenAlertPublisher sirenAlertPublisher;

    public UserControllerExceptionHandler(SirenAlertPublisher sirenAlertPublisher) {
        this.sirenAlertPublisher = sirenAlertPublisher;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        log.error("Error attempting to complete service request :: {} :: {}", ex.getClass().getName(), ex.getMessage(), ex);
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder().build();
        String description = "";
        if (ex instanceof FetchUsersDatabaseException) {
            String reason = String.format("%s - Database exception occurred while fetching users. %s", ((FetchUsersDatabaseException) ex).getErrorCode(), ex.getMessage());
            description = "Database exception occurred while fetching users";
            sirenAlertPublisher.sendMessage(SirenAlertMessage.builder()
                    .reason(reason)
                    .alertType(AlertConstants.ALERT_TYPE_DATA_BASE_FAILURE)
                    .businessImpact(AlertConstants.FETCH_USERS_BUSINESS_IMPACT)
                    .remediationLink(AlertConstants.REMEDIATION_LINK)
                    .appName(APP_NAME)
                    .build());
        }
        apiErrorResponse = apiErrorResponse.toBuilder()
                .error(Error.builder().code(ErrorConstants.INTERNAL_SERVER_ERROR).description(description).build())
                .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
