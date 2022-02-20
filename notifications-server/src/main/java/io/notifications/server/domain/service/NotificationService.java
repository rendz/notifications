package io.notifications.server.domain.service;

import io.notifications.server.domain.NotificationRepository;
import io.notifications.server.domain.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NotificationService {
    Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Mono<Void> enqueueNotification(Notification notification) {
        log.info("Enqueueing Notification={}", notification);
        // TODO check if notification is already enqueued
        return notificationRepository.save(notification);
    }

    public void deliverNotification(Notification notification) {
        log.info("Delivering Notification={}", notification);
        // TODO check if notification is already sent
        // TODO send notification
        // TODO add record fot notification
    }
}
