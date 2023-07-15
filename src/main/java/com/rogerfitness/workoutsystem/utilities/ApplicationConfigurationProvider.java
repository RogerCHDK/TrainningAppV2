package com.rogerfitness.workoutsystem.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ApplicationConfigurationProvider {
    @Value("${enable.purging.cardio.machine.scheduler.feature}")
    public boolean enablePurgingCardioMachineSchedulerFeature;
    @Value("${cardio.machine.purge.batch.size}")
    public Integer cardioMachinePurgeBatchSize;
}
