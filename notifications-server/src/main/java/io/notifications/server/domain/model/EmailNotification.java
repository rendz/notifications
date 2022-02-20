package io.notifications.server.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailNotification {
    private String sender;
    private String receiver;
    private String message;
}
