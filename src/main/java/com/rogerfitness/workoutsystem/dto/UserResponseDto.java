package com.rogerfitness.workoutsystem.dto;

import com.rogerfitness.workoutsystem.jpa.entities.WeightControlEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Integer userIdSeq;
    private String name;
    private String email;
    private List<WeightControlEntity> weightControlEntity;
}
