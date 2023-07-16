package com.rogerfitness.workoutsystem.exceptions.handlers;

import com.rogerfitness.workoutsystem.constants.AlertConstants;
import com.rogerfitness.workoutsystem.constants.WorkoutSystemApplicationConstants;
import com.rogerfitness.workoutsystem.dto.SirenAlertMessage;
import com.rogerfitness.workoutsystem.exceptions.CardioMachinePurgeException;
import com.rogerfitness.workoutsystem.exceptions.ExceptionHandler;
import com.rogerfitness.workoutsystem.exceptions.WorkoutSystemApplicationException;
import com.rogerfitness.workoutsystem.messaging.publishers.SirenAlertPublisher;
import com.rogerfitness.workoutsystem.utilities.AlertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CardioMachineExceptionHandler implements ExceptionHandler {
    private final SirenAlertPublisher sirenAlertPublisher;

    public CardioMachineExceptionHandler(SirenAlertPublisher sirenAlertPublisher) {
        this.sirenAlertPublisher = sirenAlertPublisher;
    }

    @Override
    public void handleException(WorkoutSystemApplicationException ex) {
        if (ex instanceof CardioMachinePurgeException){
            AlertUtil.handleSchedulersAlertPublisher(ex, AlertConstants.CARDIO_MACHINE_PURGE_SCHEDULER_BUSINESS_IMPACT, WorkoutSystemApplicationConstants.CARDIO_MACHINE_PURGE_SCHEDULER, sirenAlertPublisher);
        }
    }
}
