package com.hospital_app.notification_service.infra.config.bean;

import com.hospital_app.notification_service.application.port.in.SendAppointmentEmailUseCase;
import com.hospital_app.notification_service.application.port.out.db.CustomAppointmentEmailRepository;
import com.hospital_app.notification_service.application.service.SendAppointmentEmailUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppointmentEmailBean {

    @Bean
    public SendAppointmentEmailUseCase sendAppointmentEmailUseCase(CustomAppointmentEmailRepository customAppointmentEmailRepository) {
        return new SendAppointmentEmailUseCaseImpl(customAppointmentEmailRepository);
    }

}
