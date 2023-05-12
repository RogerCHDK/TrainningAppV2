package com.rogerfitness.workoutsystem.configuration;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TimedMetricsConfig {
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry){
        log.info("registering TimedAspect bean for collecting timed metrics.");
        return new TimedAspect(registry);
    }
}
