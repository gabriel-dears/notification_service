package com.hospital_app.notification_service.application.port.out.email;

public record EmailInput(
        String  to,
        String subject,
        String htmlContent
) {
}
