package io.notifications.server.domain.service;

import io.notifications.server.domain.NotificationDeliveryCache;
import io.notifications.server.domain.NotificationRepository;
import io.notifications.server.domain.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class NotificationService {
    Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final NotificationDeliveryCache deliveryCache;

    public NotificationService(NotificationRepository notificationRepository, NotificationDeliveryCache deliveryCache) {
        this.notificationRepository = notificationRepository;
        this.deliveryCache = deliveryCache;
    }

    public Mono<Void> enqueueNotification(Notification notification) {

        return deliveryCache
                .existsDeliveryStatus(String.valueOf(notification.hashCode()))
                .flatMap(exists -> {
                    log.info("Enqueueing Notification={}", notification);
                    if (Boolean.TRUE.equals(exists)) {
                        log.info("Message already delivered");
                        return Mono.empty();
                    }
                    return notificationRepository.save(notification);
                });
    }

    public Mono<?> deliverNotification(Notification notification) {
        log.info("Delivering Notification={}", notification);

        return deliveryCache
                .existsDeliveryStatus(String.valueOf(notification.hashCode()))
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        log.info("Message already delivered");
                        return Mono.just(false);
                    }

                    return deliveryCache
                            .saveDeliveryStatus(String.valueOf(notification.hashCode()))
                            .flatMap(r -> {
                                if (Instant.now().toEpochMilli() % 2 == 0) {
                                    throw new RuntimeException("Failed");
                                }
                                log.info("Delivered Notification={}", notification);

                                return Mono.just(true);
                            });
                })
                .doOnError(err -> {
                    deliveryCache
                            .deleteDeliveryStatus(String.valueOf(notification.hashCode())).subscribe();
                });
    }
}
