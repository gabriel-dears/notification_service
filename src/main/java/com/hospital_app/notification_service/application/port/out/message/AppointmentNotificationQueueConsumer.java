package com.hospital_app.notification_service.application.port.out.message;

import com.hospital_app.common.message.dto.AppointmentMessage;

public interface AppointmentNotificationQueueConsumer {
    void consume(AppointmentMessage appointmentMessage);
}
