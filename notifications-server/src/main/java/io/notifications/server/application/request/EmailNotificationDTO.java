package io.notifications.server.application.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmailNotificationDTO {
    @NotNull
    private String sender;
    @NotNull
    private String receiver;
    @NotNull
    private String message;
}
