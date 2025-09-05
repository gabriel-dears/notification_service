package com.hospital_app.notification_service.application.service;

import com.hospital_app.notification_service.domain.model.AppointmentEmail;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentEmailMapperTest {

    @Test
    void updateNonNullFields_updatesOnlyNonNullValues() {
        AppointmentEmail target = new AppointmentEmail();
        UUID originalAppointmentId = UUID.randomUUID();
        UUID originalPatientId = UUID.randomUUID();
        target.setAppointmentId(originalAppointmentId);
        target.setPatientId(originalPatientId);
        target.setPatientEmail("old@mail.com");
        target.setPatientName("Old Name");
        target.setDoctorId(UUID.randomUUID());
        target.setDoctorName("Old Doc");
        target.setDateTime(LocalDateTime.of(2025, 1, 1, 10, 0));
        target.setStatus("OLD");
        target.setNotes("old notes");
        target.setVersion(1L);

        AppointmentEmail source = new AppointmentEmail();
        // set some nulls and some new values
        source.setAppointmentId(null); // should not overwrite
        UUID newPatientId = UUID.randomUUID();
        source.setPatientId(newPatientId);
        source.setPatientEmail(null);
        source.setPatientName("New Name");
        source.setDoctorId(null);
        source.setDoctorName("New Doc");
        LocalDateTime newDate = LocalDateTime.of(2025, 2, 2, 12, 30);
        source.setDateTime(newDate);
        source.setStatus(null);
        source.setNotes("new notes");
        source.setVersion(2L);

        AppointmentEmailMapper.updateNonNullFields(source, target);

        // unchanged where source was null
        assertEquals(originalAppointmentId, target.getAppointmentId());
        assertEquals("old@mail.com", target.getPatientEmail());
        assertEquals("OLD", target.getStatus());
        // changed where source had value
        assertEquals(newPatientId, target.getPatientId());
        assertEquals("New Name", target.getPatientName());
        assertEquals("New Doc", target.getDoctorName());
        assertEquals(newDate, target.getDateTime());
        assertEquals("new notes", target.getNotes());
        assertEquals(2L, target.getVersion());
    }
}
