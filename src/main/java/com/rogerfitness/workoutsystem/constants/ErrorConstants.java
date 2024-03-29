package com.rogerfitness.workoutsystem.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorConstants {
    public static final String INTERNAL_SERVER_ERROR ="WORKSYSTEM001";
    public static final String RETRYABLE_DB_ERROR_CODE ="WORKSYSTEM002";
    public static final String NON_RETRYABLE_DB_ERROR_CODE ="WORKSYSTEM003";
    public static final String PURGE_CARDIO_MACHINE_GENERAL_ERROR_CODE ="WORKSYSTEM004";
    public static final String UNEXPECTED_EXCEPTION_ERROR_CODE ="WORKSYSTEM005";
    public static final String USERNAME_NOT_FOUND_ERROR_MESSAGE = "User information not found";
}
