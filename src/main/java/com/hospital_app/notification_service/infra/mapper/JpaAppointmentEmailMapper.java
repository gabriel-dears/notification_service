package com.hospital_app.notification_service.infra.mapper;

import com.hospital_app.notification_service.domain.model.AppointmentEmail;
import com.hospital_app.notification_service.infra.adapter.out.db.jpa.JpaAppointmentEmailEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaAppointmentEmailMapper {

    public JpaAppointmentEmailEntity toEntity(AppointmentEmail appointmentEmail) {
        if (appointmentEmail == null) {
            return null;
        }

        JpaAppointmentEmailEntity entity = new JpaAppointmentEmailEntity();
        entity.setAppointmentId(appointmentEmail.getAppointmentId());
        entity.setId(appointmentEmail.getId());
        entity.setPatientEmail(appointmentEmail.getPatientEmail());
        entity.setPatientId(appointmentEmail.getPatientId());
        entity.setDoctorId(appointmentEmail.getDoctorId());
        entity.setPatientName(appointmentEmail.getPatientName());
        entity.setDoctorName(appointmentEmail.getDoctorName());
        entity.setDateTime(appointmentEmail.getDateTime());
        entity.setStatus(appointmentEmail.getStatus());
        entity.setNotes(appointmentEmail.getNotes());
        entity.setVersion(appointmentEmail.getVersion());
        return entity;
    }

    public AppointmentEmail toDomain(JpaAppointmentEmailEntity entity) {
        if (entity == null) {
            return null;
        }

        AppointmentEmail appointmentEmail = new AppointmentEmail();
        appointmentEmail.setId(entity.getId());
        appointmentEmail.setAppointmentId(entity.getAppointmentId());
        appointmentEmail.setPatientId(entity.getPatientId());
        appointmentEmail.setPatientEmail(entity.getPatientEmail());
        appointmentEmail.setPatientName(entity.getPatientName());
        appointmentEmail.setDoctorId(entity.getDoctorId());
        appointmentEmail.setDoctorName(entity.getDoctorName());
        appointmentEmail.setDateTime(entity.getDateTime());
        appointmentEmail.setStatus(entity.getStatus());
        appointmentEmail.setNotes(entity.getNotes());
        appointmentEmail.setVersion(entity.getVersion());
        return appointmentEmail;
    }
}
