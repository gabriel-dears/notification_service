package com.hospital_app.notification_service.application.service;

import com.hospital_app.notification_service.application.port.in.SendAppointmentEmailUseCase;
import com.hospital_app.notification_service.application.port.out.db.CustomAppointmentEmailRepository;
import com.hospital_app.notification_service.domain.model.AppointmentEmail;

import java.util.Optional;

public class SendAppointmentEmailUseCaseImpl implements SendAppointmentEmailUseCase {

    private final CustomAppointmentEmailRepository customAppointmentEmailRepository;

    public SendAppointmentEmailUseCaseImpl(CustomAppointmentEmailRepository customAppointmentEmailRepository) {
        this.customAppointmentEmailRepository = customAppointmentEmailRepository;
    }

    @Override
    public void execute(AppointmentEmail appointmentEmail) {

        Optional<AppointmentEmail> existingAppointmentEmailOpt = customAppointmentEmailRepository.findByAppointmentId(appointmentEmail.getAppointmentId());

        try {
            if (existingAppointmentEmailOpt.isPresent()) {

                AppointmentEmail existingAppointmentEmail = existingAppointmentEmailOpt.get();

                // TODO: email with updated fields

            } else {

                // TODO: simple first email

            }

            customAppointmentEmailRepository.save(appointmentEmail);

        } catch (Exception ex) {

        }


    }

}
