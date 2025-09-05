package com.hospital_app.notification_service.infra.adapter.out.email;

import com.hospital_app.notification_service.application.port.out.email.EmailInput;
import com.hospital_app.notification_service.application.port.out.email.EmailSender;
import com.hospital_app.notification_service.application.port.out.email.NewAppointmentEmailSender;
import com.hospital_app.notification_service.domain.model.AppointmentEmail;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class NewAppointmentEmailSenderImpl implements NewAppointmentEmailSender {

    private final SpringTemplateEngine templateEngine;
    private final EmailSender emailSender;

    public NewAppointmentEmailSenderImpl(SpringTemplateEngine templateEngine, EmailSender emailSender) {
        this.templateEngine = templateEngine;
        this.emailSender = emailSender;
    }

    @Override
    public void sendAppointmentEmail(AppointmentEmail appointmentEmail) {
        var context = new org.thymeleaf.context.Context();
        context.setVariable("patientName", appointmentEmail.getPatientName());
        context.setVariable("appointmentDate", appointmentEmail.getDateTime());
        context.setVariable("doctorName", appointmentEmail.getDoctorName());
        context.setVariable("notes", appointmentEmail.getNotes());
        context.setVariable("appointmentId", appointmentEmail.getAppointmentId());

        String htmlContent = templateEngine.process("email/first_appointment_email", context);

        emailSender.send(new EmailInput(
                appointmentEmail.getPatientEmail(),
                "New Appointment",
                htmlContent
        ));
    }
}
