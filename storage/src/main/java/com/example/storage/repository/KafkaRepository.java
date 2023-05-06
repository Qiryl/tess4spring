package com.example.storage.repository;

import com.example.storage.model.KafkaMessage;
import com.example.storage.service.IMomRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("kafka")
public class KafkaRepository implements IMomRepository {
    private static final String TOPIC_NAME = "test2";
    private static final String GROUP_ID = "group_id";

    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void send(String id, String link, String type, String outType) {
        kafkaTemplate.send(new ProducerRecord<>(TOPIC_NAME, new KafkaMessage(id, link, type, outType)));
    }
}
