package com.hospital_app.notification_service.application.service;

import com.hospital_app.notification_service.application.port.in.SendAppointmentEmailUseCase;
import com.hospital_app.notification_service.application.port.out.db.CustomAppointmentEmailRepository;
import com.hospital_app.notification_service.application.port.out.email.AppointmentEmailForUpdateSender;
import com.hospital_app.notification_service.application.port.out.email.NewAppointmentEmailSender;
import com.hospital_app.notification_service.domain.model.AppointmentEmail;

import java.util.Optional;

public class SendAppointmentEmailUseCaseImpl implements SendAppointmentEmailUseCase {

    private final CustomAppointmentEmailRepository customAppointmentEmailRepository;
    private final NewAppointmentEmailSender newAppointmentEmailSender;
    private final AppointmentEmailForUpdateSender appointmentEmailForUpdateSender;

    public SendAppointmentEmailUseCaseImpl(CustomAppointmentEmailRepository customAppointmentEmailRepository, NewAppointmentEmailSender newAppointmentEmailSender, AppointmentEmailForUpdateSender appointmentEmailForUpdateSender) {
        this.customAppointmentEmailRepository = customAppointmentEmailRepository;
        this.newAppointmentEmailSender = newAppointmentEmailSender;
        this.appointmentEmailForUpdateSender = appointmentEmailForUpdateSender;
    }

    @Override
    public void execute(AppointmentEmail appointmentEmail) {
        Optional<AppointmentEmail> existingAppointmentEmailOpt = customAppointmentEmailRepository.findByAppointmentId(appointmentEmail.getAppointmentId());
        if (existingAppointmentEmailOpt.isPresent()) {
            AppointmentEmail existingAppointmentEmail = existingAppointmentEmailOpt.get();
            appointmentEmailForUpdateSender.sendAppointmentEmail(existingAppointmentEmail, appointmentEmail);
            AppointmentEmailMapper.updateNonNullFields(appointmentEmail, existingAppointmentEmail);
            customAppointmentEmailRepository.save(existingAppointmentEmail);
        } else {
            newAppointmentEmailSender.sendAppointmentEmail(appointmentEmail);
            customAppointmentEmailRepository.save(appointmentEmail);
        }
    }

}
