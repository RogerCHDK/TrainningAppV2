package com.rogerfitness.workoutsystem.jpa.wrapper;

import com.rogerfitness.workoutsystem.constants.ErrorConstants;
import com.rogerfitness.workoutsystem.exceptions.database.NonRetryableDBException;
import com.rogerfitness.workoutsystem.exceptions.database.RetryableDBException;
import com.rogerfitness.workoutsystem.jpa.entities.UserEntity;
import com.rogerfitness.workoutsystem.jpa.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            maxAttemptsExpression = "${db.error.retry.maxRetry}",
            backoff = @Backoff(delayExpression = "${db.error.retryDelay}")
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

    @Retryable(
            include = RetryableDBException.class,
            exclude = {NonRetryableDBException.class},
            maxAttemptsExpression = "${db.error.retry.maxRetry}",
            backoff = @Backoff(delayExpression = "${db.error.retryDelay}")
    )
    public Page<UserEntity> fetchUsers(Specification<UserEntity> specification,Pageable pageable) throws RetryableDBException, NonRetryableDBException{
        try {
            return userRepository.findAll(specification, pageable);
        }catch (DataAccessException | CannotCreateTransactionException retryableException){
            String message = "A Retryable exception occurred while fetching users";
            log.error(message,retryableException);
            throw new RetryableDBException(message, retryableException, ErrorConstants.RETRYABLE_DB_ERROR_CODE);
        }catch (Exception exception){
            String message = "A Non Retryable DB exception occurred while fetching users";
            log.error(message,exception);
            throw new NonRetryableDBException(message, exception, ErrorConstants.NON_RETRYABLE_DB_ERROR_CODE);
        }
    }

    @Retryable(
            include = RetryableDBException.class,
            exclude = {NonRetryableDBException.class},
            maxAttemptsExpression = "${db.error.retry.maxRetry}",
            backoff = @Backoff(delayExpression = "${db.error.retryDelay}")
    )
    public UserEntity fetchUserByEmail(String email) throws RetryableDBException, NonRetryableDBException{
        try {
            return userRepository
                    .getUserEntityByEmail(email)
                    .orElseThrow(
                            () -> new UsernameNotFoundException(ErrorConstants.USERNAME_NOT_FOUND_ERROR_MESSAGE)
                    );
        }catch (DataAccessException | CannotCreateTransactionException retryableException){
            String message = "A Retryable exception occurred while fetching user by email";
            log.error(message,retryableException);
            throw new RetryableDBException(message, retryableException);
        }catch (UsernameNotFoundException e){
            log.error(e.getMessage(),e);
            throw e;
        } catch (Exception exception){
            String message = "A Non Retryable DB exception occurred while fetching user by email";
            log.error(message,exception);
            throw new NonRetryableDBException(message, exception);
        }
    }
    @Retryable(
            include = RetryableDBException.class,
            exclude = {NonRetryableDBException.class},
            maxAttemptsExpression = "${db.error.retry.maxRetry}",
            backoff = @Backoff(delayExpression = "${db.error.retryDelay}")
    )
    public UserEntity createUser(UserEntity user) throws RetryableDBException, NonRetryableDBException{
        try {
            return userRepository.save(user);
        }catch (DataAccessException | CannotCreateTransactionException retryableException){
            String message = "A Retryable exception occurred while creating user";
            log.error(message,retryableException);
            throw new RetryableDBException(message, retryableException);
        }catch (Exception exception){
            String message = "A Non Retryable DB exception occurred while creating user";
            log.error(message,exception);
            throw new NonRetryableDBException(message, exception);
        }
    }
}
