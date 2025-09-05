package com.hospital_app.notification_service.infra.adapter.out.email;

import com.hospital_app.notification_service.application.port.out.email.AppointmentEmailForUpdateSender;
import com.hospital_app.notification_service.application.port.out.email.EmailInput;
import com.hospital_app.notification_service.application.port.out.email.EmailSender;
import com.hospital_app.notification_service.domain.model.AppointmentEmail;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class UpdateAppointmentEmailSender implements AppointmentEmailForUpdateSender {

    private final SpringTemplateEngine templateEngine;
    private final EmailSender emailSender;

    public UpdateAppointmentEmailSender(SpringTemplateEngine templateEngine, EmailSender emailSender) {
        this.templateEngine = templateEngine;
        this.emailSender = emailSender;
    }

    @Override
    public void sendAppointmentEmail(AppointmentEmail last, AppointmentEmail current) {

        Context context = new Context();
        context.setVariable("patientName", current.getPatientName());
        context.setVariable("changes", generateChangesText(last, current));
        context.setVariable("appointmentDate", current.getDateTime());
        context.setVariable("doctorName", current.getDoctorName());
        context.setVariable("notes", current.getNotes());
        context.setVariable("status", current.getStatus());
        context.setVariable("appointmentId", current.getAppointmentId());


        String htmlContent = templateEngine.process("email/update_appointment_email", context);

        emailSender.send(new EmailInput(
                current.getPatientEmail(),
                "Appointment Update",
                htmlContent
        ));

    }

    private String generateChangesText(AppointmentEmail last, AppointmentEmail current) {
        StringBuilder sb = new StringBuilder();
        if (!last.getDateTime().equals(current.getDateTime())) {
            sb.append("📅 Date & Time changed: ").append(last.getDateTime())
                    .append(" → ").append(current.getDateTime()).append("\n");
        }
        if (!last.getStatus().equals(current.getStatus())) {
            sb.append("⚡ Status changed: ").append(last.getStatus())
                    .append(" → ").append(current.getStatus()).append("\n");
        }
        if (!last.getNotes().equals(current.getNotes())) {
            sb.append("📝 Notes updated\n");
        }
        if (sb.isEmpty()) {
            sb.append("No major changes detected. Just a friendly reminder of your appointment!");
        }
        return sb.toString();
    }
}
