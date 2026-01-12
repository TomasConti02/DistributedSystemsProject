package com.bezkoder.spring.data.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class SpringBootDataMongodbApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataMongodbApplication.class, args);
    }
}

// --- MODELLO ---
@Document(collection = "tutorials")
class Tutorial {
    @Id private String id;
    private String title;
    public Tutorial() {}
    public Tutorial(String title) { this.title = title; }
    public String getId() { return id; }
    public String getTitle() { return title; }
}

// --- REPOSITORY ---
interface TutorialRepository extends MongoRepository<Tutorial, String> {}

// --- KAFKA RECEIVER (La novità) ---
@Service
class KafkaConsumerService {

    @Autowired
    TutorialRepository tutorialRepository;

    // Legge i messaggi dal topic "tutorials-topic"
    @KafkaListener(topics = "tutorials-topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Messaggio ricevuto da Kafka: " + message);
        // Salva il messaggio come titolo del nuovo Tutorial
        Tutorial tutorial = new Tutorial(message);
        tutorialRepository.save(tutorial);
        System.out.println("Salvato su MongoDB con successo!");
    }
}

// --- CONTROLLER (Solo per verifica GET) ---
@RestController
@RequestMapping("/api")
class TutorialController {
    @Autowired TutorialRepository tutorialRepository;

    @GetMapping("/test")
    public java.util.List<Tutorial> getAll() {
        return tutorialRepository.findAll();
    }
}