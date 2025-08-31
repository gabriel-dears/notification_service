package com.hospital_app.notification_service.infra.adapter.out.message;

import com.hospital_app.notification_service.application.port.out.message.AppointmentNotificationQueueConsumer;
import com.hospital_app.notification_service.domain.model.Appointment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AppointmentNotificationQueueConsumerImpl implements AppointmentNotificationQueueConsumer {

    @RabbitListener(queues = "notification.queue")
    @Override
    public void consume(Appointment appointment) {
        System.out.println("Appointment received: " + appointment);
    }
}
