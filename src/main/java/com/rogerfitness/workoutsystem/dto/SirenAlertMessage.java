package com.rogerfitness.workoutsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SirenAlertMessage {
    private String reason;
    private String alertType;
    private String businessImpact;
    private String remediationLink;
    private String appName;
}
