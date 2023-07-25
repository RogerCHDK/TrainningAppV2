package com.rogerfitness.workoutsystem.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AlertConstants {
    public static final String RETRYABLE_DATABASE_FAILURE_REASON = "[%s] Retryable exception occurred in database with message=%s";
    public static final String NON_RETRYABLE_DATABASE_FAILURE_REASON = "[%s] Non Retryable exception occurred in database with message=%s";
    public static final String ALERT_TYPE_DATA_BASE_FAILURE = "WorkoutSystemApplicationDataBaseFailure";
    public static final String FETCH_USERS_BUSINESS_IMPACT = "User information cannot be loaded";
    public static final String REMEDIATION_LINK = "Please follow the following link for more documentation https://workout-system-documentation.com";
    public static final String UNEXPECTED_FAILURE_REASON = "An unexpected exception occurred in scheduler: %s with message: %s";
    public static final String CARDIO_MACHINE_PURGE_SCHEDULER_BUSINESS_IMPACT = "Unable to purge expired records from cardio_machine table in WorkoutSystemApplication database";
    public static final String CREATE_USER_BUSINESS_IMPACT = "User information cannot saved, this will prevent to new users to register in the system";

}
