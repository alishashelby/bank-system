package com.businesslogic.services;

import com.businesslogic.dto.kafka.KafkaEvent;
import com.dataaccess.entities.AccountEvent;
import com.dataaccess.entities.ClientEvent;
import com.dataaccess.repositories.AccountRepository;
import com.dataaccess.repositories.ClientRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaConsumer(ClientRepository clientRepository,
                         AccountRepository accountRepository,
                         ObjectMapper objectMapper) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "client-topic")
    public void listenClient(String msg) throws JsonProcessingException {
        KafkaEvent kafkaEventDTO = objectMapper.readValue(msg, KafkaEvent.class);

        ClientEvent clientEvent = new ClientEvent();
        clientEvent.setClientLogin(kafkaEventDTO.getId());
        clientEvent.setEventType(kafkaEventDTO.getEventType());
        clientEvent.setEventData(kafkaEventDTO.getData());

        clientRepository.save(clientEvent);
    }

    @KafkaListener(topics = "account-topic")
    public void listenAccount(String msg) throws JsonProcessingException {
        KafkaEvent kafkaEventDTO = objectMapper.readValue(msg, KafkaEvent.class);

        AccountEvent accountEvent = new AccountEvent();
        accountEvent.setAccountId(Integer.parseInt(kafkaEventDTO.getId()));
        accountEvent.setEventType(kafkaEventDTO.getEventType());
        accountEvent.setEventData(kafkaEventDTO.getData());

        accountRepository.save(accountEvent);
    }
}
