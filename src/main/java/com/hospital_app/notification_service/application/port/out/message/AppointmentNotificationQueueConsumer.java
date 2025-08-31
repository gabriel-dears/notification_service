package com.hospital_app.notification_service.application.port.out.message;

import com.hospital_app.notification_service.domain.model.Appointment;

public interface AppointmentNotificationQueueConsumer {
    void consume(Appointment appointment);
}
