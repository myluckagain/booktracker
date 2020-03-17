package ru.ea.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;


@MessagingGateway
public interface NotificationGateway {

    @Gateway(requestChannel = "notifyInChanel")
    void notifyUsers(@Payload String empty);
}
