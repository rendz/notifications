package io.notifications.server.adapter.rest;

import io.notifications.server.adapter.rest.dto.EmailNotificationDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/notifications/email")
public class NotificationsController {

    @PostMapping
    Mono<Void> publishNotification(EmailNotificationDTO notificationDTO) {
        return Mono.empty();
    }
}
