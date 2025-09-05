package com.hospital_app.notification_service.application.port.in;

import com.hospital_app.notification_service.domain.model.AppointmentEmail;

public interface SendAppointmentEmailUseCase {
    void execute(AppointmentEmail appointmentEmail);
}
