package io.notifications.server.application.rest;

import io.notifications.server.application.request.EmailNotificationDTO;
import io.notifications.server.domain.NotificationRepository;
import io.notifications.server.domain.model.Notification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/notifications/email", consumes = MediaType.APPLICATION_JSON_VALUE)
public class NotificationsController {
    private final NotificationRepository notificationRepository;

    public NotificationsController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @PostMapping(consumes = {"application/json"})
    Mono<?> publishNotification(@Valid @RequestBody EmailNotificationDTO notificationDTO) {
        return notificationRepository.save(
                new Notification(
                    notificationDTO.getSender(),
                    notificationDTO.getReceiver(),
                    notificationDTO.getMessage()))
                .map(maybeEx -> maybeEx.map(Mono::error).orElse(Mono.empty()));
    }
}
