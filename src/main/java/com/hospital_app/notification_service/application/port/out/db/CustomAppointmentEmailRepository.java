package com.hospital_app.notification_service.application.port.out.db;

import com.hospital_app.notification_service.domain.model.AppointmentEmail;

import java.util.Optional;
import java.util.UUID;

public interface CustomAppointmentEmailRepository {

    Optional<AppointmentEmail> findByAppointmentId(UUID id);

    AppointmentEmail save(AppointmentEmail appointmentEmail);

    void deleteObsolete();
}
