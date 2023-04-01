package com.rogerfitness.workoutsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Integer userIdSeq;
    private String name;
    private String email;
}
