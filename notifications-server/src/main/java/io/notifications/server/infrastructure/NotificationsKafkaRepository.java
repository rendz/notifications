package io.notifications.server.infrastructure;

import io.notifications.server.domain.NotificationRepository;
import io.notifications.server.domain.model.Notification;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@Component
public class NotificationsKafkaRepository implements NotificationRepository {
    public static final String NOTIFICATIONS_TOPIC = "notifications";
    private final ReactiveKafkaProducerTemplate<String, Notification> kafkaTemplate;

    public NotificationsKafkaRepository(ReactiveKafkaProducerTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private static Mono<Void> mapKafkaResult(SenderResult<Void> res) {
        return res.exception() != null
                ? Mono.error(res.exception())
                : Mono.empty();
    }

    @Override
    public Mono<Void> save(Notification notification) {
        return kafkaTemplate
                .send(
                        NOTIFICATIONS_TOPIC,
                        "key-" + notification.hashCode(),
                        notification)
                .flatMap(NotificationsKafkaRepository::mapKafkaResult);
    }
}
