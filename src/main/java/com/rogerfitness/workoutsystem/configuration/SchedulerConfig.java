package com.rogerfitness.workoutsystem.configuration;

import com.rogerfitness.workoutsystem.exceptions.handlers.SchedulerErrorHandler;
import com.rogerfitness.workoutsystem.utilities.ApplicationConfigurationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
@Slf4j
public class SchedulerConfig implements SchedulingConfigurer {
    private final ApplicationConfigurationProvider configurationProvider;
    private final SchedulerErrorHandler schedulerErrorHandler;

    public SchedulerConfig(ApplicationConfigurationProvider configurationProvider, SchedulerErrorHandler schedulerErrorHandler) {
        this.configurationProvider = configurationProvider;
        this.schedulerErrorHandler = schedulerErrorHandler;
    }

    @Bean(name = "schedulerClock")
    public Clock schedulerClock(){
        log.info("Application schedulers are using timezone {}", configurationProvider.getSchedulerTimeZone());
        ZoneId zoneId = ZoneId.of(configurationProvider.getSchedulerTimeZone());
        return Clock.system(zoneId);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setErrorHandler(schedulerErrorHandler);
        threadPoolTaskScheduler.setPoolSize(configurationProvider.getSchedulerPoolMaxSize());
        threadPoolTaskScheduler.setThreadNamePrefix("scheduled-task-async-");
        threadPoolTaskScheduler.initialize();
        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
