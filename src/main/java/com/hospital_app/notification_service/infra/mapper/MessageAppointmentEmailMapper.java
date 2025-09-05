package com.hospital_app.notification_service.infra.mapper;

import com.hospital_app.common.message.dto.AppointmentMessage;
import com.hospital_app.notification_service.domain.model.AppointmentEmail;
import org.springframework.stereotype.Component;

@Component
public class MessageAppointmentEmailMapper {

    public AppointmentEmail toEmail(AppointmentMessage appointmentMessage) {
        if (appointmentMessage == null) {
            return null;
        }
        AppointmentEmail appointmentEmail = new AppointmentEmail();
        appointmentEmail.setPatientId(appointmentMessage.getPatientId());
        appointmentEmail.setAppointmentId(appointmentMessage.getId());
        appointmentEmail.setPatientName(appointmentMessage.getPatientName());
        appointmentEmail.setPatientEmail(appointmentMessage.getPatientEmail());
        appointmentEmail.setDoctorName(appointmentMessage.getDoctorName());
        appointmentEmail.setDoctorId(appointmentMessage.getDoctorId());
        appointmentEmail.setDateTime(appointmentMessage.getDateTime());
        appointmentEmail.setStatus(appointmentMessage.getStatus());
        appointmentEmail.setNotes(appointmentMessage.getNotes());
        appointmentEmail.setVersion(appointmentMessage.getVersion());
        return appointmentEmail;
    }

}
