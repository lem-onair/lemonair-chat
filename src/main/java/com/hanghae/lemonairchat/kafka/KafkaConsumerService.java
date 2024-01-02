package com.hanghae.lemonairchat.kafka;

import com.hanghae.lemonairchat.entity.Chat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.ReceiverOptions;

@Service
public class KafkaConsumerService {
    private String bootstrapServer = "localhost:9091,localhost:9092,localhost:9093";

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    public ReactiveKafkaConsumerTemplate<String, Chat> reactiveKafkaConsumerTemplate(String roomId) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "chat-consumer-group" + UUID.randomUUID());
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keySerializer);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueSerializer);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        ReceiverOptions<String, Chat> basicReceiverOptions = ReceiverOptions.create(config);
        basicReceiverOptions.subscription(Collections.singletonList(roomId));

        return new ReactiveKafkaConsumerTemplate<>(basicReceiverOptions);
    }
}
