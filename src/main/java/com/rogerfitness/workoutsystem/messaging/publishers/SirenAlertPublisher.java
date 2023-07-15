package com.rogerfitness.workoutsystem.messaging.publishers;

import com.rogerfitness.workoutsystem.dto.SirenAlertMessage;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class SirenAlertPublisher {
    @Value("${siren.alert.topic.name}")
    private String topicName;
    private final KafkaTemplate<String, SirenAlertMessage> kafkaTemplate;

    public SirenAlertPublisher(KafkaTemplate<String, SirenAlertMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //    public void sendMessage(String message) {
//        ListenableFuture<SendResult<String, String>> response = kafkaTemplate.send(topicName, message);
//        response.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                log.warn("Unable to deliver message [{}]. {}", message, ex.getMessage(), ex);
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                log.info("Message [{}] delivered to topic: {} with timestamp: {}", message, result.getRecordMetadata().topic(), result.getRecordMetadata().timestamp());
//            }
//        });
//    }
    public void sendMessage(SirenAlertMessage message) {
        ListenableFuture<SendResult<String, SirenAlertMessage>> response = kafkaTemplate.send(topicName, message);
        response.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to deliver message [{}]. {}", message, ex.getMessage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, SirenAlertMessage> result) {
                log.info("Message [{}] delivered to topic: {} with timestamp: {}", message, result.getRecordMetadata().topic(), result.getRecordMetadata().timestamp());
            }
        });
    }
}
