package com.rogerfitness.workoutsystem.service;

import com.rogerfitness.workoutsystem.converter.UserConverter;
import com.rogerfitness.workoutsystem.dto.UserResponseDto;
import com.rogerfitness.workoutsystem.exceptions.NonRetryableDBException;
import com.rogerfitness.workoutsystem.exceptions.RetryableDBException;
import com.rogerfitness.workoutsystem.jpa.entities.UserEntity;
import com.rogerfitness.workoutsystem.jpa.wrapper.UserRetryableWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRetryableWrapper userRetryableWrapper;
    private final UserConverter userConverter;
//    TODO: add handling expception, create swagger page, create Grafana dashboard, create siren alert, test
    public List<UserResponseDto> getAllUsers() throws NonRetryableDBException, RetryableDBException {
        List<UserEntity> userEntityList = userRetryableWrapper.getAllUsers();
        return userEntityList.stream().map(userConverter::convertFromEntity).collect(Collectors.toList());
    }
}
