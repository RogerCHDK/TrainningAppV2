package com.rogerfitness.workoutsystem.exceptions.handlers;

import com.rogerfitness.workoutsystem.constants.AlertConstants;
import com.rogerfitness.workoutsystem.constants.ErrorConstants;
import com.rogerfitness.workoutsystem.controllers.UserController;
import com.rogerfitness.workoutsystem.dto.SirenAlertMessage;
import com.rogerfitness.workoutsystem.exceptions.CreateUserDatabaseException;
import com.rogerfitness.workoutsystem.exceptions.Error;
import com.rogerfitness.workoutsystem.exceptions.FetchUsersDatabaseException;
import com.rogerfitness.workoutsystem.exceptions.database.NonRetryableDBException;
import com.rogerfitness.workoutsystem.exceptions.database.RetryableDBException;
import com.rogerfitness.workoutsystem.messaging.publishers.SirenAlertPublisher;
import com.rogerfitness.workoutsystem.responses.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.List;
import java.util.stream.Collectors;

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
        String description = ex.getMessage();
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
        }else if (ex instanceof CreateUserDatabaseException) {
            String reason = String.format("%s - Database exception occurred while saving user information. %s", ((CreateUserDatabaseException) ex).getErrorCode(), ex.getMessage());
            description = "Database exception occurred while saving user information";
            sirenAlertPublisher.sendMessage(SirenAlertMessage.builder()
                    .reason(reason)
                    .alertType(AlertConstants.ALERT_TYPE_DATA_BASE_FAILURE)
                    .businessImpact(AlertConstants.CREATE_USER_BUSINESS_IMPACT)
                    .remediationLink(AlertConstants.REMEDIATION_LINK)
                    .appName(APP_NAME)
                    .build());
        }else if (ex instanceof RetryableDBException) {
            String reason = String.format("%s - Retryable Database exception occurred while calling UserController endpoint. %s", ((RetryableDBException) ex).getErrorCode(), ex.getMessage());
            description = "Retryable Database exception occurred while calling UserController endpoint";
            sirenAlertPublisher.sendMessage(SirenAlertMessage.builder()
                    .reason(reason)
                    .alertType(AlertConstants.ALERT_TYPE_DATA_BASE_FAILURE)
                    .businessImpact(AlertConstants.USER_ERROR_BUSINESS_IMPACT)
                    .remediationLink(AlertConstants.REMEDIATION_LINK)
                    .appName(APP_NAME)
                    .build());

        }else if (ex instanceof NonRetryableDBException) {
            String reason = String.format("%s - Non-Retryable Database exception occurred while calling UserController endpoint. %s", ((NonRetryableDBException) ex).getErrorCode(), ex.getMessage());
            description = "Non-Retryable Database exception occurred while calling UserController endpoint";
            sirenAlertPublisher.sendMessage(SirenAlertMessage.builder()
                    .reason(reason)
                    .alertType(AlertConstants.ALERT_TYPE_DATA_BASE_FAILURE)
                    .businessImpact(AlertConstants.USER_ERROR_BUSINESS_IMPACT)
                    .remediationLink(AlertConstants.REMEDIATION_LINK)
                    .appName(APP_NAME)
                    .build());
        }else if (ex instanceof MethodArgumentNotValidException){
            List<Error> errorList = ((MethodArgumentNotValidException) ex)
                    .getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .map(error -> Error.builder().description(error).build())
                    .collect(Collectors.toList());
            apiErrorResponse = apiErrorResponse.toBuilder()
                    .errors(errorList)
                    .build();
            return ResponseEntity.badRequest().body(apiErrorResponse);
        }
        apiErrorResponse = apiErrorResponse.toBuilder()
                .error(Error.builder().code(ErrorConstants.INTERNAL_SERVER_ERROR).description(description).build())
                .build();
        return ResponseEntity.internalServerError().body(apiErrorResponse);
    }
}
