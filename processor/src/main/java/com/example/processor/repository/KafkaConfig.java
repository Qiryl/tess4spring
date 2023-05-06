package com.example.processor.repository;

//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.DefaultErrorHandler;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableKafka
//@Configuration
//public class KafkaConfig {
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//    @Bean
//    public ConsumerFactory<String, KafkaMessage> consumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
////        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        return new DefaultKafkaConsumerFactory<>(config);
//    }
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setCommonErrorHandler(new DefaultErrorHandler());
//        return factory;
//    }
//}