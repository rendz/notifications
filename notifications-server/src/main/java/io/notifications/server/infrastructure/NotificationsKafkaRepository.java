package io.notifications.server.infrastructure;

import io.notifications.server.domain.NotificationRepository;
import io.notifications.server.domain.model.EmailNotification;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class NotificationsKafkaRepository implements NotificationRepository {
    public static final String EMAIL_NOTIFICATIONS_TOPIC = "email-notifications";
    private final ReactiveKafkaProducerTemplate<String, EmailNotification> kafkaTemplate;

    public NotificationsKafkaRepository(ReactiveKafkaProducerTemplate<String, EmailNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Mono<Optional<Exception>> save(EmailNotification notification) {
        return kafkaTemplate
                .send(
                        EMAIL_NOTIFICATIONS_TOPIC,
                        "key-" + notification.hashCode(),
                        notification)
                .map(r -> Optional.ofNullable(r.exception()));
    }
}
