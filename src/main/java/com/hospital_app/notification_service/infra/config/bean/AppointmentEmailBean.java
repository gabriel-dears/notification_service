package com.hospital_app.notification_service.infra.config.bean;

import com.hospital_app.notification_service.application.port.in.RemoveObsoleteAppointmentEmailUseCase;
import com.hospital_app.notification_service.application.port.in.SendAppointmentEmailUseCase;
import com.hospital_app.notification_service.application.port.out.db.CustomAppointmentEmailRepository;
import com.hospital_app.notification_service.application.port.out.email.AppointmentEmailForUpdateSender;
import com.hospital_app.notification_service.application.port.out.email.NewAppointmentEmailSender;
import com.hospital_app.notification_service.application.service.RemoveObsoleteItemsService;
import com.hospital_app.notification_service.application.service.SendAppointmentEmailUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppointmentEmailBean {

    @Bean
    public SendAppointmentEmailUseCase sendAppointmentEmailUseCase(CustomAppointmentEmailRepository customAppointmentEmailRepository, NewAppointmentEmailSender newAppointmentEmailSender, AppointmentEmailForUpdateSender appointmentEmailForUpdateSender) {
        return new SendAppointmentEmailUseCaseImpl(customAppointmentEmailRepository, newAppointmentEmailSender, appointmentEmailForUpdateSender);
    }

    @Bean
    public RemoveObsoleteAppointmentEmailUseCase removeObsoleteAppointmentEmailUseCase(CustomAppointmentEmailRepository customAppointmentEmailRepository) {
        return new RemoveObsoleteItemsService(customAppointmentEmailRepository);
    }


}
