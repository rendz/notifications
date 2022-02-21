package io.notifications.server.domain;

import reactor.core.publisher.Mono;

public interface NotificationDeliveryCache {
    Mono<Void> saveDeliveryStatus(String notificationIdentifier);

    Mono<Boolean> existsDeliveryStatus(String notificationIdentifier);
}
