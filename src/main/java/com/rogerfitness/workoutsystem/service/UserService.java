package com.rogerfitness.workoutsystem.service;

import com.rogerfitness.workoutsystem.converter.UserConverter;
import com.rogerfitness.workoutsystem.dto.UserResponseDto;
import com.rogerfitness.workoutsystem.exceptions.NonRetryableDBException;
import com.rogerfitness.workoutsystem.exceptions.RetryableDBException;
import com.rogerfitness.workoutsystem.jpa.entities.UserEntity;
import com.rogerfitness.workoutsystem.jpa.searchcriteria.UserSearchCriteria;
import com.rogerfitness.workoutsystem.jpa.specifications.UserSpecification;
import com.rogerfitness.workoutsystem.jpa.wrapper.UserRetryableWrapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRetryableWrapper userRetryableWrapper;
    private final UserConverter userConverter;
//    TODO: add handle exception, create swagger page, create Grafana dashboard, create siren alert, test
    public List<UserResponseDto> getAllUsers() throws NonRetryableDBException, RetryableDBException {
        List<UserEntity> userEntityList = userRetryableWrapper.getAllUsers();
        return userEntityList.stream().map(userConverter::convertFromEntity).collect(Collectors.toList());
    }

    public Page<UserResponseDto> fetchUsers(UserSearchCriteria searchCriteria) throws NonRetryableDBException, RetryableDBException {
        Specification<UserEntity> specification = UserSpecification.filterByName(searchCriteria.getName())
                .and(UserSpecification.filterByUserId(searchCriteria.getUserIdSeq()))
                .and(UserSpecification.filterByEmail(searchCriteria.getEmail()))
                .and(UserSpecification.filterByWeight(searchCriteria.getWeight()));
        Pageable pageable = searchCriteria.createPageable();
        Page<UserEntity> userEntityPage;
        userEntityPage = userRetryableWrapper.fetchUsers(specification, pageable);
        return userEntityPage.map(userConverter::convertFromEntity);
    }
}
