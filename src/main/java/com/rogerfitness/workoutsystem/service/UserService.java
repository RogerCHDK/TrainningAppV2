package com.rogerfitness.workoutsystem.service;

import com.rogerfitness.workoutsystem.constants.PrometheusConstants;
import com.rogerfitness.workoutsystem.converter.UserConverter;
import com.rogerfitness.workoutsystem.dto.UserResponseDto;
import com.rogerfitness.workoutsystem.exceptions.FetchUsersDatabaseException;
import com.rogerfitness.workoutsystem.exceptions.NonRetryableDBException;
import com.rogerfitness.workoutsystem.exceptions.RetryableDBException;
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
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@AllArgsConstructor
@Service
public class UserService {
    private final UserRetryableWrapper userRetryableWrapper;
    private final UserConverter userConverter;
    private final EntityManager entityManager;
//    TODO: add handle exception, create swagger page, create Grafana dashboard, create siren alert, test
    public List<UserResponseDto> getAllUsers() throws NonRetryableDBException, RetryableDBException {
        List<UserEntity> userEntityList = userRetryableWrapper.getAllUsers();
        return userEntityList.stream().map(userConverter::convertFromEntity).collect(Collectors.toList());
    }

    @Timed(PrometheusConstants.USER_SERVICE_FETCH_USERS_LATENCY)
    public Page<UserResponseDto> fetchUsers(UserSearchCriteria searchCriteria) throws Exception {
        Specification<UserEntity> specification = UserSpecification.filterByName(searchCriteria.getName())
                .and(UserSpecification.filterByUserId(searchCriteria.getUserIdSeq()))
                .and(UserSpecification.filterByEmail(searchCriteria.getEmail()))
                .and(UserSpecification.filterByWeight(searchCriteria.getWeight(), entityManager));
        Pageable pageable = searchCriteria.createPageable();
        Page<UserEntity> userEntityPage;
        try {
            userEntityPage = userRetryableWrapper.fetchUsers(specification, pageable);
        }catch (PropertyReferenceException referenceException){
            log.warn("Invalid search property given: {} defaulting", searchCriteria.getSortBy(), referenceException);
            searchCriteria.useDefaultSort();
            userEntityPage = userRetryableWrapper.fetchUsers(specification, searchCriteria.createPageable());
        }catch (RetryableDBException | NonRetryableDBException dbException){
            log.error("[UserService] fetchUsers() DB exception occurs", dbException);
            throw new FetchUsersDatabaseException(dbException);
        }
        catch (Exception exception) {
            log.error("[UserService] fetchUsers() general exception occurs", exception);
            throw exception;
        }
        return userEntityPage.map(userConverter::convertFromEntity);
    }
}
