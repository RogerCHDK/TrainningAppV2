package com.rogerfitness.workoutsystem.jpa.wrapper;

import com.rogerfitness.workoutsystem.constants.ErrorConstants;
import com.rogerfitness.workoutsystem.exceptions.database.NonRetryableDBException;
import com.rogerfitness.workoutsystem.exceptions.database.RetryableDBException;
import com.rogerfitness.workoutsystem.jpa.entities.CardioMachineEntity;
import com.rogerfitness.workoutsystem.jpa.repositories.CardioMachineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;

@Service
@Slf4j
public class CardioMachineRetryableWrapper {
    private final CardioMachineRepository cardioMachineRepository;
    private final CardioMachineTransactionalWrapper cardioMachineTransactionalWrapper;

    public CardioMachineRetryableWrapper(CardioMachineRepository cardioMachineRepository, CardioMachineTransactionalWrapper cardioMachineTransactionalWrapper) {
        this.cardioMachineRepository = cardioMachineRepository;
        this.cardioMachineTransactionalWrapper = cardioMachineTransactionalWrapper;
    }

    @Retryable(
            include = RetryableDBException.class,
            exclude = {NonRetryableDBException.class},
            maxAttemptsExpression = "3",
            backoff = @Backoff(delay = 1000)
    )
    public Page<CardioMachineEntity> fetchCardioMachineByIsExpired(boolean isExpired, Pageable pageable) throws RetryableDBException, NonRetryableDBException {
        try {
            return cardioMachineRepository.findCardioMachineEntitiesByIsExpired(isExpired, pageable);
        }catch (DataAccessException | CannotCreateTransactionException | TransactionSystemException retryableDBException){
            log.error("Retryable DB exception occurred in fetchCardioMachineByIsExpired", retryableDBException);
            throw new RetryableDBException(retryableDBException.getMessage(), retryableDBException, ErrorConstants.RETRYABLE_DB_ERROR_CODE);
        }catch (Exception nonRetryableDBException){
            log.error("Non-Retryable DB exception occurred in fetchCardioMachineByIsExpired", nonRetryableDBException);
            throw new NonRetryableDBException(nonRetryableDBException.getMessage(), nonRetryableDBException, ErrorConstants.NON_RETRYABLE_DB_ERROR_CODE);
        }
    }

    @Retryable(
            include = RetryableDBException.class,
            exclude = {NonRetryableDBException.class},
            maxAttemptsExpression = "3",
            backoff = @Backoff(delay = 1000)
    )
    public void purgeExpiredCardioMachine(List<Integer> cardioMachineIdsList) throws RetryableDBException, NonRetryableDBException {
        try {
            cardioMachineTransactionalWrapper.purgeExpiredCardioMachine(cardioMachineIdsList);
        }catch (DataAccessException | CannotCreateTransactionException | TransactionSystemException retryableDBException){
            log.error("Retryable DB exception occurred in purgeExpiredCardioMachine", retryableDBException);
            throw new RetryableDBException(retryableDBException.getMessage(), retryableDBException);
        }catch (Exception nonRetryableDBException){
            log.error("Non-Retryable DB exception occurred in purgeExpiredCardioMachine", nonRetryableDBException);
            throw new NonRetryableDBException(nonRetryableDBException.getMessage(), nonRetryableDBException);
        }
    }
}
