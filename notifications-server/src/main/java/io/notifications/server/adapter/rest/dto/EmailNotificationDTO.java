package io.notifications.server.adapter.rest.dto;

import lombok.Data;

@Data
public class EmailNotificationDTO {
    private String sender;
    private String receiver;
    private String message;
}
