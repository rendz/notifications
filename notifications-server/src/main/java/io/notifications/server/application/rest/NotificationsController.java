package io.notifications.server.application.rest;

import io.notifications.server.application.request.NotificationDTO;
import io.notifications.server.domain.model.Notification;
import io.notifications.server.domain.service.NotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/notifications/email")
public class NotificationsController {
    private final NotificationService notificationService;

    public NotificationsController(NotificationService notificationService) {
        this.notificationService = notificationService;

    }

    @PostMapping(consumes = {"application/json"})
    Mono<?> publishNotification(@Valid @RequestBody NotificationDTO notificationDTO) {
        return notificationService.enqueueNotification(
                new Notification(
                    notificationDTO.getSender(),
                    notificationDTO.getReceiver(),
                    notificationDTO.getMessage()));
    }
}
