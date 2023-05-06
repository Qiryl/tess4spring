package com.example.processor.service;

import com.example.processor.repository.KafkaMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
//    @KafkaListener(topics = "test", groupId = "group_id")
//    public void consume(KafkaMessage msg) throws Exception {
//        try {
//            System.out.println("Consumed message: " + msg);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to fetch data from kafka:", e);
//        }
//    }
}
