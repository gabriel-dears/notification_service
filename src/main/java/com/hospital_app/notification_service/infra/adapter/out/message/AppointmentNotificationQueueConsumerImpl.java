package com.hospital_app.notification_service.infra.adapter.out.message;

import com.hospital_app.common.message.dto.AppointmentMessage;
import com.hospital_app.notification_service.infra.config.message.rabbitmq.RabbitMQNotificationConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AppointmentNotificationQueueConsumerImpl {

    @RabbitListener(queues = RabbitMQNotificationConfig.NOTIFICATION_QUEUE)
    public void consume(AppointmentMessage appointmentMessage) {
        System.out.println("Appointment received: " + appointmentMessage);
        System.out.println("Appointment id: " + appointmentMessage.getId());
        System.out.println("Appointment doctor id: " + appointmentMessage.getDoctorId());
        System.out.println("Appointment patient id: " + appointmentMessage.getPatientId());
        System.out.println("Appointment status: " + appointmentMessage.getStatus());
        System.out.println("Appointment notes: " + appointmentMessage.getNotes());
        System.out.println("Appointment date time: " + appointmentMessage.getDateTime());
        System.out.println("Appointment version: " + appointmentMessage.getVersion());
        System.out.println("Appointment doctor name: " + appointmentMessage.getDoctorName());
        System.out.println("Appointment patient name: " + appointmentMessage.getPatientName());
        System.out.println("Appointment patient email: " + appointmentMessage.getPatientEmail());
        // TODO: create appointment-history-service (same deps + graphql)
        // TODO: consume messages exactly as notification service
        // TODO: create any config in the common module?
        // TODO: store last sent email? -> compare... status updated from X to Y, Notes.. from Z to A??
    }
}
