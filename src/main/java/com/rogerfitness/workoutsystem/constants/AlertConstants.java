package com.rogerfitness.workoutsystem.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AlertConstants {
    public static final String RETRYABLE_DATABASE_FAILURE_REASON = "Retryable exception occurred in database with message=%s";
    public static final String ALERT_TYPE_DATA_BASE_FAILURE = "WorkoutSystemApplicationDataBaseFailure";
    public static final String FETCH_USERS_BUSINESS_IMPACT = "User information cannot be loaded";
    public static final String REMEDIATION_LINK = "Please follow the following link for more documentation https://workout-system-documentation.com";

}
