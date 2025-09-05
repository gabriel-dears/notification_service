package com.hospital_app.notification_service.application.port.out.email;

import com.hospital_app.notification_service.domain.model.AppointmentEmail;

public interface NewAppointmentEmailSender {
    void sendAppointmentEmail(AppointmentEmail appointmentEmail);
}
