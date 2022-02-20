package io.notifications.server.domain;

import io.notifications.server.domain.model.Notification;
import reactor.core.publisher.Mono;

public interface NotificationRepository {
    Mono<Void> save(Notification notification);
}
