package io.notifications.server.domain;

import io.notifications.server.domain.model.Notification;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface NotificationRepository {
    Mono<Optional<Exception>> save(Notification notification);
}
