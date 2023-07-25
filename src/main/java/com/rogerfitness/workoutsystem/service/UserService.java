package com.rogerfitness.workoutsystem.service;

import com.rogerfitness.workoutsystem.constants.PrometheusConstants;
import com.rogerfitness.workoutsystem.converter.UserConverter;
import com.rogerfitness.workoutsystem.dto.AuthenticationRequest;
import com.rogerfitness.workoutsystem.dto.AuthenticationResponse;
import com.rogerfitness.workoutsystem.dto.CreateUserRequest;
import com.rogerfitness.workoutsystem.dto.UserResponseDto;
import com.rogerfitness.workoutsystem.exceptions.user.CreateUserDatabaseException;
import com.rogerfitness.workoutsystem.exceptions.user.FetchUsersDatabaseException;
import com.rogerfitness.workoutsystem.exceptions.database.NonRetryableDBException;
import com.rogerfitness.workoutsystem.exceptions.database.RetryableDBException;
import com.rogerfitness.workoutsystem.jpa.entities.UserSecurity;
import com.rogerfitness.workoutsystem.jpa.entities.UserEntity;
import com.rogerfitness.workoutsystem.jpa.searchcriteria.UserSearchCriteria;
import com.rogerfitness.workoutsystem.jpa.specifications.UserSpecification;
import com.rogerfitness.workoutsystem.jpa.wrapper.UserRetryableWrapper;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    private final UserRetryableWrapper userRetryableWrapper;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public List<UserResponseDto> getAllUsers() throws NonRetryableDBException, RetryableDBException {
        List<UserEntity> userEntityList = userRetryableWrapper.getAllUsers();
        return userEntityList.stream().map(userConverter::convertFromEntity).collect(Collectors.toList());
    }

    @Timed(PrometheusConstants.USER_SERVICE_FETCH_USERS_LATENCY)
    public Page<UserResponseDto> fetchUsers(UserSearchCriteria searchCriteria) throws Exception {
        Specification<UserEntity> specification = UserSpecification.filterByName(searchCriteria.getName())
                .and(UserSpecification.filterByUserId(searchCriteria.getUserIdSeq()))
                .and(UserSpecification.filterByEmail(searchCriteria.getEmail()))
                .and(UserSpecification.filterByWeight(searchCriteria.getWeight()));
        Pageable pageable = searchCriteria.createPageable();
        Page<UserEntity> userEntityPage;
        try {
            userEntityPage = userRetryableWrapper.fetchUsers(specification, pageable);
        }catch (PropertyReferenceException referenceException){
            log.warn("Invalid search property given: {} defaulting", searchCriteria.getSortBy(), referenceException);
            searchCriteria.useDefaultSort();
            userEntityPage = userRetryableWrapper.fetchUsers(specification, searchCriteria.createPageable());
        }catch (RetryableDBException retryableDBException){
            log.error("[UserService] fetchUsers() retryable DB exception occurs", retryableDBException);
            throw new FetchUsersDatabaseException(retryableDBException.getMessage(), retryableDBException, retryableDBException.getErrorCode());
        }catch (NonRetryableDBException nonRetryableDBException){
            log.error("[UserService] fetchUsers() non-retryable DB exception occurs", nonRetryableDBException);
            throw new FetchUsersDatabaseException(nonRetryableDBException.getMessage(), nonRetryableDBException, nonRetryableDBException.getErrorCode());
        }
        catch (Exception exception) {
            log.error("[UserService] fetchUsers() general exception occurs", exception);
            throw exception;
        }
        return userEntityPage.map(userConverter::convertFromEntity);
    }

    public AuthenticationResponse createUser(CreateUserRequest request) throws CreateUserDatabaseException {
        UserEntity user = UserEntity
                .builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        try {
            UserEntity userEntity = userRetryableWrapper.createUser(user);
            UserSecurity userSecurity = new UserSecurity(userEntity);
            String token = jwtService.generateToken(userSecurity);
            return AuthenticationResponse
                    .builder()
                    .accessToken(token)
                    .build();
        } catch (RetryableDBException retryableDBException) {
            log.error("[UserService] createUser() retryable DB exception occurs", retryableDBException);
            throw new CreateUserDatabaseException(retryableDBException.getMessage(), retryableDBException, retryableDBException.getErrorCode());
        } catch (NonRetryableDBException nonRetryableDBException) {
            log.error("[UserService] createUser() non-retryable DB exception occurs", nonRetryableDBException);
            throw new CreateUserDatabaseException(nonRetryableDBException.getMessage(), nonRetryableDBException, nonRetryableDBException.getErrorCode());
        } catch (Exception exception) {
            log.error("[UserService] createUser() general exception occurs", exception);
            throw exception;
        }
    }
    public AuthenticationResponse authenticateUser(AuthenticationRequest request) throws RetryableDBException, NonRetryableDBException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            UserEntity userEntity = userRetryableWrapper.fetchUserByEmail(request.getEmail());
            UserSecurity userSecurity = new UserSecurity(userEntity);
            String token = jwtService.generateToken(userSecurity);
            return AuthenticationResponse
                    .builder()
                    .accessToken(token)
                    .build();
        } catch (RetryableDBException retryableDBException) {
            log.error("[UserService] authenticateUser() retryable DB exception occurs", retryableDBException);
            throw retryableDBException;
        } catch (NonRetryableDBException nonRetryableDBException) {
            log.error("[UserService] authenticateUser() non-retryable DB exception occurs", nonRetryableDBException);
            throw nonRetryableDBException;
        } catch (Exception exception) {
            log.error("[UserService] authenticateUser() general exception occurs", exception);
            throw exception;
        }
    }
}
