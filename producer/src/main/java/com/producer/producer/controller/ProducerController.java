package com.producer.producer.controller;

import com.producer.producer.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publish")
public class ProducerController {

    private final KafkaProducerService producerService;

    // Utilizziamo la Constructor Injection invece di @Autowired sul campo (Best Practice)
    @Autowired
    public ProducerController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public String postMessage(@RequestBody String message) {
        producerService.sendMessage(message);
        return "Messaggio inviato correttamente al topic Kafka!";
    }
}
