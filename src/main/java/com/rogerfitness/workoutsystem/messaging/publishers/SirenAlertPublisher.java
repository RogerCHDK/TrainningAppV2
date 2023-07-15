package com.rogerfitness.workoutsystem.messaging.publishers;

import com.rogerfitness.workoutsystem.dto.SirenAlertMessage;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Objects;

@Slf4j
@Service
public class SirenAlertPublisher {
    @Value("${siren.alert.topic.name}")
    private String topicName;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SirenAlertPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

        public void sendMessage(Object message) {
        ListenableFuture<SendResult<String, Object>> response = kafkaTemplate.send(topicName, message);
        response.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to deliver message [{}]. {}", message, ex.getMessage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Message [{}] delivered to topic: {} with timestamp: {}", message, result.getRecordMetadata().topic(), result.getRecordMetadata().timestamp());
            }
        });
    }
}
