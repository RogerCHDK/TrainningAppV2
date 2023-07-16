package com.rogerfitness.workoutsystem.utilities;

import com.rogerfitness.workoutsystem.constants.AlertConstants;
import com.rogerfitness.workoutsystem.constants.ErrorConstants;
import com.rogerfitness.workoutsystem.constants.WorkoutSystemApplicationConstants;
import com.rogerfitness.workoutsystem.dto.SirenAlertMessage;
import com.rogerfitness.workoutsystem.exceptions.WorkoutSystemApplicationException;
import com.rogerfitness.workoutsystem.messaging.publishers.SirenAlertPublisher;


public class AlertUtil {
    public static void handleSchedulersAlertPublisher(WorkoutSystemApplicationException ex, String businessImpact, String schedulerName, SirenAlertPublisher sirenAlertPublisher) {
        String reason;
        String alertType;
        if (ex.getErrorCode().equalsIgnoreCase(ErrorConstants.RETRYABLE_DB_ERROR_CODE)) {
            reason = ErrorConstants.RETRYABLE_DB_ERROR_CODE + "-" + String.format(AlertConstants.RETRYABLE_DATABASE_FAILURE_REASON, schedulerName, ex.getMessage());
            alertType = AlertConstants.ALERT_TYPE_DATA_BASE_FAILURE;
        } else if (ex.getErrorCode().equalsIgnoreCase(ErrorConstants.NON_RETRYABLE_DB_ERROR_CODE)) {
            reason = ErrorConstants.NON_RETRYABLE_DB_ERROR_CODE + "-" + String.format(AlertConstants.NON_RETRYABLE_DATABASE_FAILURE_REASON, schedulerName, ex.getMessage());
            alertType = AlertConstants.ALERT_TYPE_DATA_BASE_FAILURE;
        } else {
            reason = ErrorConstants.UNEXPECTED_EXCEPTION_ERROR_CODE + "-" + String.format(AlertConstants.NON_RETRYABLE_DATABASE_FAILURE_REASON, schedulerName, ex.getMessage());
            alertType = AlertConstants.ALERT_TYPE_DATA_BASE_FAILURE;
        }
        sirenAlertPublisher.sendMessage(
            SirenAlertMessage.builder()
            .alertType(alertType)
            .businessImpact(businessImpact)
            .reason(reason)
            .remediationLink(AlertConstants.REMEDIATION_LINK)
            .appName(WorkoutSystemApplicationConstants.APP_NAME)
            .build()
        );
    }
}
