package com.rogerfitness.workoutsystem.schedulers;

import com.rogerfitness.workoutsystem.service.CardioMachineService;
import com.rogerfitness.workoutsystem.utilities.ApplicationConfigurationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CardioMachinePurgeScheduler {
    private final ApplicationConfigurationProvider configurationProvider;
    private final CardioMachineService cardioMachineService;

    public CardioMachinePurgeScheduler(ApplicationConfigurationProvider configurationProvider, CardioMachineService cardioMachineService) {
        this.configurationProvider = configurationProvider;
        this.cardioMachineService = cardioMachineService;
    }

    @Scheduled(fixedDelayString = "${cardio.machine.purge.scheduler.fixedDelay.interval.in.milliseconds}")
    public void doRunCardioMachinePurgeScheduler(){
        if (configurationProvider.isEnablePurgingCardioMachineSchedulerFeature()){
            cardioMachineService.processCardioMachinePurge();
        }else {
            log.warn("CardioMachinePurgeScheduler is disabled. To enable it update the application configuration.");
        }
    }
}
