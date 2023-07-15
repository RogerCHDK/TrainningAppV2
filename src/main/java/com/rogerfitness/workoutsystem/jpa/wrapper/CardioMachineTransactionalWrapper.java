package com.rogerfitness.workoutsystem.jpa.wrapper;

import com.rogerfitness.workoutsystem.jpa.repositories.CardioMachineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CardioMachineTransactionalWrapper {
    private final CardioMachineRepository cardioMachineRepository;

    public CardioMachineTransactionalWrapper(CardioMachineRepository cardioMachineRepository) {
        this.cardioMachineRepository = cardioMachineRepository;
    }

    public void purgeExpiredCardioMachine(List<Integer> cardioMachineIdsList) {
        log.trace("[CardioMachineTransactionalWrapper] Inside purgeExpiredCardioMachine");
        cardioMachineRepository.deleteAllByIdInBatch(cardioMachineIdsList);
        log.trace("[CardioMachineTransactionalWrapper] Exit purgeExpiredCardioMachine");
    }
}
