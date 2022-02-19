package io.notifications.server.adapter.rest.dto;

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
