package com.producer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}

// --- KAFKA PRODUCER SERVICE ---
@Service
class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "tutorials-topic";

    public void sendMessage(String message) {
        System.out.println("Invio messaggio a Kafka: " + message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}

// --- REST CONTROLLER PER TESTARE L'INVIO ---
@RestController
@RequestMapping("/api/publish")
class ProducerController {

    @Autowired
    KafkaProducerService producerService;

    @PostMapping
    public String postMessage(@RequestBody String message) {
        producerService.sendMessage(message);
        return "Messaggio inviato correttamente al topic Kafka!";
    }
}