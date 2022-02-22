package io.notifications.server.domain;

import reactor.core.publisher.Mono;

public interface NotificationDeliveryCache {
    Mono<Long> deleteDeliveryStatus(String notificationIdentifier);
    Mono<Long> saveDeliveryStatus(String notificationIdentifier);

    Mono<Boolean> existsDeliveryStatus(String notificationIdentifier);
}
