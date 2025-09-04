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
        // TODO: create db -> integrate with the application -> entity -> domain model
        // TODO: integrate flow with the db... only after sending (no failures) - simulate or comment sending in the first moment... success scenario,
        //  store last email sent (same appointment id)? -> compare... status updated from X to Y, Notes.. from Z to A??
        // TODO: configure application to send email -> verify how to do it and create email template
        // TODO: create a job to periodically analyze certain status of COMPLETION and remove from the db -> every 3 months at 03:00am??
        // TODO: configure env variables for all projects
        // TODO: mTLS for gRPC communication and check how to do it with graphQL and RabbitMQ
        // TODO: verify unit tests - notification project
        // TODO: documentation -> swagger...README file... javadocs... verify for each project and create google doc (microservices interaction and hexagonal arc.)
        // repo: https://github.com/gabriel-dears/hospital_app
    }
}
