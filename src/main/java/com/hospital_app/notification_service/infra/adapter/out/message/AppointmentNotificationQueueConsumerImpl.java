package com.hospital_app.notification_service.infra.adapter.out.message;

import com.hospital_app.common.message.dto.AppointmentMessage;
import com.hospital_app.notification_service.application.port.in.SendAppointmentEmailUseCase;
import com.hospital_app.notification_service.domain.model.AppointmentEmail;
import com.hospital_app.notification_service.infra.config.message.rabbitmq.RabbitMQNotificationConfig;
import com.hospital_app.notification_service.infra.mapper.MessageAppointmentEmailMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AppointmentNotificationQueueConsumerImpl {

    private final SendAppointmentEmailUseCase sendAppointmentEmailUseCase;
    private final MessageAppointmentEmailMapper messageAppointmentEmailMapper;

    public AppointmentNotificationQueueConsumerImpl(SendAppointmentEmailUseCase sendAppointmentEmailUseCase, MessageAppointmentEmailMapper messageAppointmentEmailMapper) {
        this.sendAppointmentEmailUseCase = sendAppointmentEmailUseCase;
        this.messageAppointmentEmailMapper = messageAppointmentEmailMapper;
    }

    @RabbitListener(queues = RabbitMQNotificationConfig.NOTIFICATION_QUEUE)
    public void consume(AppointmentMessage appointmentMessage) {
        AppointmentEmail appointmentEmail = messageAppointmentEmailMapper.toEmail(appointmentMessage);
        sendAppointmentEmailUseCase.execute(appointmentEmail);
        // TODO: documentation -> swagger...README file... javadocs... verify for each project and create google doc (microservices interaction and hexagonal arc.)
        // repo: https://github.com/gabriel-dears/hospital_app
    }
}
