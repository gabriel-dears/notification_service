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
            System.out.println("Appointment sent - email: " + existingAppointmentEmail);
            System.out.println("Appointment id: " + existingAppointmentEmail.getId());
            System.out.println("Appointment doctor id: " + existingAppointmentEmail.getDoctorId());
            System.out.println("Appointment patient id: " + existingAppointmentEmail.getPatientId());
            System.out.println("Appointment status: " + existingAppointmentEmail.getStatus());
            System.out.println("Appointment notes: " + existingAppointmentEmail.getNotes());
            System.out.println("Appointment date time: " + existingAppointmentEmail.getDateTime());
            System.out.println("Appointment version: " + existingAppointmentEmail.getVersion());
            System.out.println("Appointment doctor name: " + existingAppointmentEmail.getDoctorName());
            System.out.println("Appointment patient name: " + existingAppointmentEmail.getPatientName());
            System.out.println("Appointment patient email: " + existingAppointmentEmail.getPatientEmail());

            customAppointmentEmailRepository.save(existingAppointmentEmail);
        } else {
            newAppointmentEmailSender.sendAppointmentEmail(appointmentEmail);
            customAppointmentEmailRepository.save(appointmentEmail);
        }
    }

}
