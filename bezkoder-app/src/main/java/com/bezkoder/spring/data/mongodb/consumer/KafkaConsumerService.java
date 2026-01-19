package com.bezkoder.spring.data.mongodb.consumer;

import com.bezkoder.spring.data.mongodb.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private TutorialService tutorialService;

    @KafkaListener(topics = "tutorials-topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Messaggio ricevuto da Kafka: " + message);
        tutorialService.saveTutorial(message);
        System.out.println("Salvato tramite Service con successo!");
    }
}
