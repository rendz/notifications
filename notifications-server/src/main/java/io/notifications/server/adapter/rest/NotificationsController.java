package io.notifications.server.adapter.rest;

import io.notifications.server.adapter.rest.dto.EmailNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(NotificationsController.class);

    @PostMapping(consumes = {"application/json"})
    Mono<Void> publishNotification(@Valid @RequestBody EmailNotificationDTO notificationDTO) {
        logger.info(notificationDTO.toString());
        return Mono.empty();
    }
}
