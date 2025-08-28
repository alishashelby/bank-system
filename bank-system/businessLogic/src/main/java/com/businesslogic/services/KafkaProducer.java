package com.businesslogic.services;

import com.businesslogic.dto.kafka.KafkaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String CLIENT_TOPIC = "client-topic";
    private static final String ACCOUNT_TOPIC = "account-topic";

    @Autowired
    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendClientMessage(KafkaEvent kafkaEvent) {
        kafkaTemplate.send(CLIENT_TOPIC, kafkaEvent);
    }

    public void sendAccountMessage(KafkaEvent kafkaEvent) {
        kafkaTemplate.send(ACCOUNT_TOPIC, kafkaEvent);
    }
}
