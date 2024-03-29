package com.rogerfitness.workoutsystem.messaging.subscriptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SirenAlertSubscription {
    @KafkaListener(topics = "workout-test", groupId = "com.rogerfitness.workoutsystem")
    public void listenGroupFoo(String message) {
//Todo: Receive and save the message into a table
        log.info("Received Message: {}", message);
    }
}
