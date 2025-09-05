package com.hospital_app.notification_service.application.port.out.email;

public interface EmailSender {
    void send(EmailInput emailInput);
}
