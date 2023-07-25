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
    @Value("${scheduler.run.timeZone}")
    public String schedulerTimeZone;
    @Value("${scheduler.pool.max.size}")
    public Integer schedulerPoolMaxSize;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration.in.minutes}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
}
