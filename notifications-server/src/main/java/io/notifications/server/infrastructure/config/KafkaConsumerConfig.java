package io.notifications.server.infrastructure.config;

import io.notifications.server.domain.model.Notification;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ReceiverOptions<String, Notification> kafkaReceiverOptions(KafkaProperties kafkaProperties) {
        ReceiverOptions<String, Notification> basicReceiverOptions = ReceiverOptions
                .create(kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList("notifications"));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, Notification> notificationsConsumerTemplate(
            ReceiverOptions<String, Notification> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }
}
