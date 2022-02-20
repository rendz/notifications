package io.notifications.server.infrastructure;

import io.notifications.server.domain.NotificationRepository;
import io.notifications.server.domain.model.Notification;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class NotificationsKafkaRepository implements NotificationRepository {
    public static final String EMAIL_NOTIFICATIONS_TOPIC = "email-notifications";
    private final ReactiveKafkaProducerTemplate<String, Notification> kafkaTemplate;

    public NotificationsKafkaRepository(ReactiveKafkaProducerTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Mono<Optional<Exception>> save(Notification notification) {
        return kafkaTemplate
                .send(
                        EMAIL_NOTIFICATIONS_TOPIC,
                        "key-" + notification.hashCode(),
                        notification)
                .map(r -> Optional.ofNullable(r.exception()));
    }
}
