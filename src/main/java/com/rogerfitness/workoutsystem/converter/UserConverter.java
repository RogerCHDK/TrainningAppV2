package com.rogerfitness.workoutsystem.converter;

import com.rogerfitness.workoutsystem.dto.UserResponseDto;
import com.rogerfitness.workoutsystem.jpa.entities.UserEntity;
import com.rogerfitness.workoutsystem.utilities.IOConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements IOConverter<UserResponseDto, UserEntity> {
    @Override
    public UserEntity convertFromDto(UserResponseDto userResponseDto) {
        UserEntity userEntity = UserEntity.builder().build();
        BeanUtils.copyProperties(userResponseDto, userEntity);
        return userEntity;
    }

    @Override
    public UserResponseDto convertFromEntity(UserEntity userEntity) {
        UserResponseDto userResponseDto = UserResponseDto.builder().build();
        BeanUtils.copyProperties(userEntity, userResponseDto);
        return userResponseDto;
    }
}
