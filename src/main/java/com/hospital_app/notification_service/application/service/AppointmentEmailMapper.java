package com.hospital_app.notification_service.application.service;

import com.hospital_app.notification_service.domain.model.AppointmentEmail;

public class AppointmentEmailMapper {

    public static void updateNonNullFields(AppointmentEmail source, AppointmentEmail target) {
        if (source.getAppointmentId() != null) {
            target.setAppointmentId(source.getAppointmentId());
        }
        if (source.getPatientId() != null) {
            target.setPatientId(source.getPatientId());
        }
        if (source.getPatientEmail() != null) {
            target.setPatientEmail(source.getPatientEmail());
        }
        if (source.getPatientName() != null) {
            target.setPatientName(source.getPatientName());
        }
        if (source.getDoctorId() != null) {
            target.setDoctorId(source.getDoctorId());
        }
        if (source.getDoctorName() != null) {
            target.setDoctorName(source.getDoctorName());
        }
        if (source.getDateTime() != null) {
            target.setDateTime(source.getDateTime());
        }
        if (source.getStatus() != null) {
            target.setStatus(source.getStatus());
        }
        if (source.getNotes() != null) {
            target.setNotes(source.getNotes());
        }
        if (source.getVersion() != null) {
            target.setVersion(source.getVersion());
        }
    }
}

