package com.rogerfitness.workoutsystem.jpa.wrapper;

import com.rogerfitness.workoutsystem.exceptions.NonRetryableDBException;
import com.rogerfitness.workoutsystem.exceptions.RetryableDBException;
import com.rogerfitness.workoutsystem.jpa.entities.UserEntity;
import com.rogerfitness.workoutsystem.jpa.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class UserRetryableWrapper {

    private final UserRepository userRepository;

    @Retryable(
            include = RetryableDBException.class,
            exclude = {NonRetryableDBException.class},
            maxAttemptsExpression = "3",
            backoff = @Backoff(delay = 1000)
    )
    public List<UserEntity> getAllUsers() throws RetryableDBException, NonRetryableDBException{
        List<UserEntity> userEntityList = new ArrayList<>();
        try {
            userEntityList = userRepository.findAll();
        }catch (DataAccessException | CannotCreateTransactionException retryableException){
            String message = "A Retryable exception occurred while getting all users";
            log.error(message,retryableException);
            throw new RetryableDBException(message, retryableException);
        }catch (Exception exception){
            String message = "A Non Retryable DB exception occurred while getting all users";
            log.error(message,exception);
            throw new NonRetryableDBException(message, exception);
        }
        return userEntityList;
    }
}
