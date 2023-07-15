package com.rogerfitness.workoutsystem.service;

import com.rogerfitness.workoutsystem.constants.AlertConstants;
import com.rogerfitness.workoutsystem.constants.ErrorConstants;
import com.rogerfitness.workoutsystem.exceptions.CardioMachinePurgeException;
import com.rogerfitness.workoutsystem.exceptions.NonRetryableDBException;
import com.rogerfitness.workoutsystem.exceptions.RetryableDBException;
import com.rogerfitness.workoutsystem.jpa.entities.CardioMachineEntity;
import com.rogerfitness.workoutsystem.jpa.wrapper.CardioMachineRetryableWrapper;
import com.rogerfitness.workoutsystem.utilities.ApplicationConfigurationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class CardioMachineService {

    public static final int PURGING_BATCH_SIZE = 50;
    private final ApplicationConfigurationProvider configurationProvider;
    private final CardioMachineRetryableWrapper cardioMachineRetryableWrapper;

    public CardioMachineService(ApplicationConfigurationProvider configurationProvider, CardioMachineRetryableWrapper cardioMachineRetryableWrapper) {
        this.configurationProvider = configurationProvider;
        this.cardioMachineRetryableWrapper = cardioMachineRetryableWrapper;
    }

    public void processCardioMachinePurge() throws CardioMachinePurgeException {
        log.info("Entered processCardioMachinePurge");
        try {
            int batchSize = Objects.nonNull(configurationProvider.getCardioMachinePurgeBatchSize())
                    ? configurationProvider.getCardioMachinePurgeBatchSize()
                    : PURGING_BATCH_SIZE;

            Pageable pageable = PageRequest.of(0, batchSize);

            Page<Integer> cardioMachineIdPage = cardioMachineRetryableWrapper.fetchCardioMachineByIsExpired(false, pageable).map(CardioMachineEntity::getCardioMachineIdSeq);

            if (cardioMachineIdPage.getTotalPages() > 0) {
                int batchNumber = 1;
                do {
                    List<Integer> cardioMachineIds = cardioMachineIdPage.getContent();
                    cardioMachineRetryableWrapper.purgeExpiredCardioMachine(cardioMachineIds);
                    log.info("Cardio Machine Deleted rows count: {}, batch number: {}", cardioMachineIds.size(), batchNumber++);
                    cardioMachineIdPage = cardioMachineRetryableWrapper.fetchCardioMachineByIsExpired(false, pageable).map(CardioMachineEntity::getCardioMachineIdSeq);
                } while (cardioMachineIdPage.getTotalElements() > 0);
            } else {
                log.info("No data available to delete. Cardio Machine Deleted rows count: 0");
            }
        }catch (RetryableDBException retryableDBException){
            log.error(String.format(AlertConstants.RETRYABLE_DATABASE_FAILURE_REASON, CardioMachineService.class.getSimpleName(), retryableDBException.getMessage()), retryableDBException);
            throw new CardioMachinePurgeException(retryableDBException.getMessage(), retryableDBException, ErrorConstants.RETRYABLE_DB_ERROR_CODE);
        }catch (NonRetryableDBException nonRetryableDBException){
            log.error(String.format(AlertConstants.RETRYABLE_DATABASE_FAILURE_REASON, CardioMachineService.class.getSimpleName(), nonRetryableDBException.getMessage()), nonRetryableDBException);
            throw new CardioMachinePurgeException(nonRetryableDBException.getMessage(), nonRetryableDBException, ErrorConstants.NON_RETRYABLE_DB_ERROR_CODE);
        }catch (Exception exception){
            log.error(String.format("[%s] General exception while purging cardioMachine records", CardioMachineService.class.getSimpleName()), exception);
            throw new CardioMachinePurgeException(exception.getMessage(), exception, ErrorConstants.PURGE_CARDIO_MACHINE_GENERAL_ERROR_CODE);
        }
    }

}
